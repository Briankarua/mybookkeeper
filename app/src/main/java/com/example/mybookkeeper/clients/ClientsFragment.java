package com.example.mybookkeeper.clients;

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
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybookkeeper.MainActivity;
import com.example.mybookkeeper.R;
import com.example.mybookkeeper.SqliteDatabase;
import com.example.mybookkeeper.accounts.Account;
import com.example.mybookkeeper.managers.Manager;
import com.example.mybookkeeper.managers.RefreshableFragment;
import com.example.mybookkeeper.subaccounts.SubAccount;

import java.util.ArrayList;

public class ClientsFragment extends Fragment implements RefreshableFragment {

    private SqliteDatabase mDatabase;
    RecyclerView clientView;
    Button buttonAdd;

    String subAccNameFromSubaccs;
    int mngIdFromSubacc;
    int acntIdFromSubaccs;
    int subAccIdFromSubacc;

    public ClientsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_clients, container, false);

        SubAccount subaccounts = null;
        Bundle args = getArguments();
        buttonAdd = v.findViewById(R.id.btnAdd);
        clientView = v.findViewById(R.id.myClientList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        clientView.setLayoutManager(linearLayoutManager);
        clientView.setHasFixedSize(true);
        mDatabase = new SqliteDatabase(getActivity());

        if (getArguments() != null) {
            subAccIdFromSubacc = getArguments().getInt("subAccIdFromSubacc");
            subAccNameFromSubaccs = getArguments().getString("subAccNameFromSubaccs");
            acntIdFromSubaccs = getArguments().getInt("acntIdFromSubaccs");
            mngIdFromSubacc = getArguments().getInt("mngIdFromSubacc");

            ((MainActivity) getActivity()).getSupportActionBar().setTitle("Client's List for ");
            ((MainActivity) getActivity()).getSupportActionBar().setSubtitle("SUBACC: " + subAccNameFromSubaccs);
        }else{
            ((MainActivity) getActivity()).getSupportActionBar().setTitle("NO ACCOUNT SELECTED");
            ((MainActivity) getActivity()).getSupportActionBar().setSubtitle("SELECTED ACCOUNT NOT FOUND");
        }
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

        ArrayList<Client> allClients = mDatabase.listClients(subAccIdFromSubacc);
        if (allClients.size() > 0) {
            clientView.setVisibility(View.VISIBLE);
            ClientAdapter mAdapter = new ClientAdapter(getActivity(), this, allClients);
            clientView.setAdapter(mAdapter);
        }
        else {
            clientView.setVisibility(View.GONE);
            Toast.makeText(getActivity(), "There is no client in the database. Start adding now - " + subAccIdFromSubacc, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void navigateToManagers(Manager manager) {

    }

    @Override
    public void navigateToCreaateAccount() {

    }

    @Override
    public void navigateToClients(SubAccount subaccounts) {

    }

    @Override
    public void navigateToSubAccountDialog(SubAccount subaccounts) {

    }

    @Override
    public void navigateToAccounts(Manager manager) {

    }

    @Override
    public void navigateToSubAccounts(Account account) {

    }

    @Override
    public void navigateToClientsDialog(Client client) {
        Bundle args = new Bundle();
        args.putInt("clientIDFromClients", client.getId());
        args.putString("clientNameFromClients", client.getCltName());
        args.putInt("subAccIdFromClients", subAccIdFromSubacc);
        args.putString("subAccNameFromClients", subAccNameFromSubaccs);
        args.putInt("acntIdFromClients", acntIdFromSubaccs);
        args.putInt("mngIdFromFromClients", mngIdFromSubacc);
        NavHostFragment.findNavController(ClientsFragment.this)
                .navigate(R.id.action_ClientsFragment_to_TransactionsFragment, args);
    }

    @Override
    public void navigateToManagerDialog(Manager manager) {

    }

    @Override
    public void navigateToAccountReceiptDialog(Account account) {

    }

    @Override
    public void navigateToAccountReceiptDFragment(Account account) {

    }

    private void addTaskDialog() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View subView = inflater.inflate(R.layout.add_clients, null);
        final EditText clnameField = subView.findViewById(R.id.entercltName);
        final EditText clientMgidField = subView.findViewById(R.id.entercltMgid);
        clientMgidField.setText(mngIdFromSubacc + "");
        final EditText claccIdField = subView.findViewById(R.id.entercltaccId);
        claccIdField.setText(acntIdFromSubaccs + "");
        final EditText cltsubIdField = subView.findViewById(R.id.entercltSubId);
        cltsubIdField.setText(subAccIdFromSubacc + "");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add new CLIENT");
        builder.setView(subView);
        builder.create();
        builder.setPositiveButton("ADD CLIENT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String cltname = clnameField.getText().toString();
                final int clientMgid = Integer.parseInt(clientMgidField.getText().toString());
                final int cltaccId = Integer.parseInt(claccIdField.getText().toString());
                final int cltsubId = Integer.parseInt(cltsubIdField.getText().toString());
                if (TextUtils.isEmpty(cltname)) {
                    Toast.makeText(getActivity(), "Something went wrong. Check your input values", Toast.LENGTH_LONG).show();
                }
                else {
                    Client newClient = new Client(cltname, clientMgid, cltaccId, cltsubId);
                    mDatabase.addClients(newClient);
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