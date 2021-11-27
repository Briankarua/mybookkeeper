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

import java.util.ArrayList;

public class ManagersFragment extends Fragment {

    private SqliteDatabase mDatabase;
    RecyclerView contactView;
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
        // Inflate the layout for this fragment
        getActivity().setTitle("Managers");
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_managers, container, false);
         contactView = v.findViewById(R.id.myContactList);
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
    private void refresh(){

        ArrayList<Contacts> allContacts = mDatabase.listContacts();
        if (allContacts.size() > 0) {
            contactView.setVisibility(View.VISIBLE);
            ContactAdapter mAdapter = new ContactAdapter(getActivity(), allContacts);
            contactView.setAdapter(mAdapter);
        }
        else {
            contactView.setVisibility(View.GONE);
            Toast.makeText(getActivity(), "There is no contact in the database. Start adding now", Toast.LENGTH_LONG).show();
        }
    }
    private void addTaskDialog() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View subView = inflater.inflate(R.layout.add_contacts, null);
        final EditText nameField = subView.findViewById(R.id.enterName);
        final EditText noField = subView.findViewById(R.id.enterJob);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add new MANAGER");
        builder.setView(subView);
        builder.create();
        builder.setPositiveButton("ADD CONTACT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String name = nameField.getText().toString();
                final String ph_no = noField.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(getActivity(), "Something went wrong. Check your input values", Toast.LENGTH_LONG).show();
                }
                else {
                    Contacts newContact = new Contacts(name, ph_no);
                    mDatabase.addContacts(newContact);
                    refresh();
//                    Intent i = new Intent(getActivity(),ManagersFragment.class);
//                    startActivity(i);
/*                    finish();
                    startActivity(getIntent());*/
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