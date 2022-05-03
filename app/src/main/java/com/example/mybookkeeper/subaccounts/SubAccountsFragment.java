package com.example.mybookkeeper.subaccounts;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybookkeeper.MainActivity;
import com.example.mybookkeeper.R;
import com.example.mybookkeeper.SqliteDatabase;
import com.example.mybookkeeper.accounts.Account;
import com.example.mybookkeeper.clients.Client;
import com.example.mybookkeeper.managers.Manager;
import com.example.mybookkeeper.uiutils.RefreshableFragment;
import com.example.mybookkeeper.uiutils.RefreshableNavigatable;

import java.util.ArrayList;

public class SubAccountsFragment extends Fragment implements RefreshableNavigatable {

    private SqliteDatabase mDatabase;
    RecyclerView subaccountView;
    Button buttonAdd, btnSmryDialog;
    SubAccount subAccount;
    String chooser;

    String accNameFromAccs;
    int mngIdFromAccs;
    int accIdFromAccs;


    public SubAccountsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_subaccounts, container, false);
        buttonAdd = v.findViewById(R.id.btnAdd);
        btnSmryDialog = v.findViewById(R.id.btnRctSumry);
        Bundle args = getArguments();
        subaccountView = v.findViewById(R.id.mySubAccountsList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        subaccountView.setLayoutManager(linearLayoutManager);
        subaccountView.setHasFixedSize(true);
        mDatabase = new SqliteDatabase(getActivity());

        if (getArguments() != null) {
            chooser = getArguments().getString("originPage");
            if (chooser.equalsIgnoreCase("FromAccsAdmin")){
                buttonAdd.setVisibility(View.VISIBLE);
            }else if (chooser.equalsIgnoreCase("FromAccsLgn")){
                buttonAdd.setVisibility(View.GONE);
            }else if (chooser.equalsIgnoreCase("FromAccsPwd")){
                buttonAdd.setVisibility(View.GONE);
            }
            accIdFromAccs = getArguments().getInt("accIdFromAccs");
            accNameFromAccs = getArguments().getString("accNameFromAccs");
            mngIdFromAccs = getArguments().getInt("mngIdFromAccs");
            ((MainActivity) getActivity()).getSupportActionBar().setTitle("SubAccount's List for ");;
            ((MainActivity) getActivity()).getSupportActionBar().setSubtitle("ACC:- " + accNameFromAccs);

        }else{
            ((MainActivity) getActivity()).getSupportActionBar().setTitle("NO ACCOUNT SELECTED");
            ((MainActivity) getActivity()).getSupportActionBar().setSubtitle("SELECTED ACCOUNT NOT FOUND");
        }

        refresh();

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTaskDialog();
            }
        });
        btnSmryDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SubAccountsFragment.this)
                        .navigate(R.id.action_SubAccountsFragment_to_SubAccountDialogFragment, args);
            }
        });
        return v;
    }
    public void refresh(){

        ArrayList<SubAccount> allSubAccounts = mDatabase.listSubAccounts(accIdFromAccs);
        if (allSubAccounts.size() > 0) {
            subaccountView.setVisibility(View.VISIBLE);
            SubAccountAdapter mAdapter = new SubAccountAdapter(getActivity(), this, allSubAccounts, getArguments().getString("originPage"));
            subaccountView.setAdapter(mAdapter);
        }
        else {
            subaccountView.setVisibility(View.GONE);
            Toast.makeText(getActivity(), "There is no subaccount in the database. Start adding now", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void navigateToAccounts(Manager manager) {

    }

    @Override
    public void navigateToCreaateAccount() {

    }

    @Override
    public void navigateToSubAccounts(Account account) {

    }

    @Override
    public void navigateToClientsDialog(Client client) {

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

    @Override
    public void navigateToClients(SubAccount subaccounts) {
        Bundle args = new Bundle();
        args.putInt("subAccIdFromSubacc", subaccounts.getSubAccountId());
        args.putString("subAccNameFromSubaccs", subaccounts.getSubAccName());
        args.putInt("acntIdFromSubaccs", accIdFromAccs);
        args.putInt("mngIdFromSubacc", mngIdFromAccs);
        NavHostFragment.findNavController(SubAccountsFragment.this)
                .navigate(R.id.action_SubAccountFragment_to_ClientFragment, args);
    }

    @Override
    public void navigateToSubAccountDialog(SubAccount subaccounts) {

    }

    private void addTaskDialog() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View subView = inflater.inflate(R.layout.add_subaccount, null);
        final EditText subaccnameField = subView.findViewById(R.id.entersacName);
        final EditText mgidField = subView.findViewById(R.id.entersacMgId);
        mgidField.setText(mngIdFromAccs+"");
        final EditText accField = subView.findViewById(R.id.entersacAccId);
        accField.setText(accIdFromAccs+"");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add new SUBACCOUNT");
        builder.setView(subView);
        builder.create();
        builder.setPositiveButton("ADD SUBACCOUNTF", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String subacNme = subaccnameField.getText().toString();
                final int mngid = mngIdFromAccs;
                final int accid = accIdFromAccs;
                if (TextUtils.isEmpty(subacNme)) {
                    Toast.makeText(getActivity(), "Something went wrong. Check your input values", Toast.LENGTH_LONG).show();
                } else {
                    SubAccount newSubAccount = new SubAccount(subacNme, mngid, accid);
                    mDatabase.addSubAccounts(newSubAccount);
                    refresh();
                }
            }
        }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
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

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ((MainActivity)getActivity()).getSupportActionBar().show();
    }
}