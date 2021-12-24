package com.example.mybookkeeper.accounts;

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
import com.example.mybookkeeper.accounts.AccountAdapter;
import com.example.mybookkeeper.accounts.Account;
import com.example.mybookkeeper.managers.Refreshable;

import java.util.ArrayList;

public class AccountsFragment extends Fragment implements Refreshable {

    private SqliteDatabase mDatabase;
    RecyclerView contactView;
    public AccountsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Accounts");
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_accounts, container, false);
         contactView = v.findViewById(R.id.myAccountList);
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
    public void refresh(){

        ArrayList<Account> allAccounts = mDatabase.listAccounts();
        if (allAccounts.size() > 0) {
            contactView.setVisibility(View.VISIBLE);
            AccountAdapter mAdapter = new AccountAdapter(getActivity(), this, allAccounts);
            contactView.setAdapter(mAdapter);
        }
        else {
            contactView.setVisibility(View.GONE);
            Toast.makeText(getActivity(), "There is no contact in the database. Start adding now", Toast.LENGTH_LONG).show();
        }
    }
    private void addTaskDialog() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View subView = inflater.inflate(R.layout.add_accounts, null);
        final EditText nameField = subView.findViewById(R.id.enterAccName);
        final EditText descField = subView.findViewById(R.id.enterDescription);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add new ACCOUNT");
        builder.setView(subView);
        builder.create();
        builder.setPositiveButton("ADD ACCOUNT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String accountName = nameField.getText().toString();
                final String accDescription = descField.getText().toString();
                if (TextUtils.isEmpty(accountName)) {
                    Toast.makeText(getActivity(), "Something went wrong. Check your input values", Toast.LENGTH_LONG).show();
                }
                else {
                    Account newAccount = new Account(accountName, accDescription);
                    mDatabase.addAccounts(newAccount);
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