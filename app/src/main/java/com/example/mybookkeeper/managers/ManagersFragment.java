package com.example.mybookkeeper.managers;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybookkeeper.R;
import com.example.mybookkeeper.SqliteDatabase;

import java.util.ArrayList;

public class ManagersFragment extends Fragment implements Refreshable {

    RecyclerView contactView;
    private SqliteDatabase mDatabase;

    public ManagersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Managers");
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_managers, container, false);
        contactView = v.findViewById(R.id.myManagerList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        contactView.setLayoutManager(linearLayoutManager);
        contactView.setHasFixedSize(true);
        mDatabase = new SqliteDatabase(getActivity());
        refresh();
        Button btnAdd = v.findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTaskDialog();
            }
        });
        return v;
    }

    public void refresh() {

        ArrayList<Managers> allContacts = mDatabase.listManagers();
        if (allContacts.size() > 0) {
            contactView.setVisibility(View.VISIBLE);
            ManagerAdapter mAdapter = new ManagerAdapter(getActivity(), this, allContacts);
            contactView.setAdapter(mAdapter);
        } else {
            contactView.setVisibility(View.GONE);
            Toast.makeText(getActivity(), "There is no contact in the database. Start adding now", Toast.LENGTH_LONG).show();
        }
    }

    private void addTaskDialog() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View subView = inflater.inflate(R.layout.add_managers, null);
        final EditText nameField = subView.findViewById(R.id.enterName);
        final EditText noField = subView.findViewById(R.id.enterTask);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add new MANAGER");
        builder.setView(subView);
        builder.create();
        builder.setPositiveButton("ADD MANAGER", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String name = nameField.getText().toString();
                final String ph_no = noField.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(getActivity(), "Something went wrong. Check your input values", Toast.LENGTH_LONG).show();
                } else {
                    Managers newContact = new Managers(name, ph_no);
                    mDatabase.addManagers(newContact);
                    refresh();
                }
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), "Task cancelled", Toast.LENGTH_LONG).show();
            }
        });
        builder.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mDatabase != null) {
            mDatabase.close();
        }
    }

}