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
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybookkeeper.MainActivity;
import com.example.mybookkeeper.R;
import com.example.mybookkeeper.SqliteDatabase;
import com.example.mybookkeeper.clients.Client;
import com.example.mybookkeeper.managers.Manager;
import com.example.mybookkeeper.managers.RefreshableFragment;
import com.example.mybookkeeper.subaccounts.SubAccount;

import java.util.ArrayList;

public class AccountsFragment extends Fragment implements RefreshableFragment {

    private SqliteDatabase mDatabase;
    RecyclerView accountView;
    Account account;
    String chooser;
    Button buttonAdd, bReceipt, bExpense;
    EditText eMgid;

    int counter = 5;

    String mngNameFromMngs;
    int mngIdFromMngs;
    String mngPhoneFromMngs;

    int mngIdFromHomeLgn;
    String mngNameFromHomeLgn;
    String phoneFromHomeLgn;

    int mngIdFromNewPwd;
    String mngNameFromNewPwd;
    String mngPhoneFromNewPwd;

    int mngIdFromAccounts;

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

        Account account = null;
        Bundle args = getArguments();

        View v = inflater.inflate(R.layout.fragment_accounts, container, false);
        buttonAdd = v.findViewById(R.id.btnAdd);
        bReceipt = v.findViewById(R.id.btnRctSumry);
        bExpense = v.findViewById(R.id.btnExpSmry);
        accountView = v.findViewById(R.id.myAccountList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        accountView.setLayoutManager(linearLayoutManager);
        accountView.setHasFixedSize(true);
        eMgid = v.findViewById(R.id.enterMgid);
        mDatabase = new SqliteDatabase(getActivity());
        if (getArguments() != null) {
            chooser = getArguments().getString("originPage");
            if (chooser.equals("FromMngs")){
                mngIdFromMngs = getArguments().getInt("mngIdFromMngs");
                mngNameFromMngs = getArguments().getString("mngNameFromMngs");
                mngPhoneFromMngs = getArguments().getString("mngPhoneFromMngs");
                buttonAdd.setVisibility(View.VISIBLE);
                ((MainActivity) getActivity()).getSupportActionBar().setTitle("Account's List for ");
                ((MainActivity) getActivity()).getSupportActionBar().setSubtitle("ACCOUNT:- " + mngNameFromMngs);
            }else if (chooser.equals("FromHomeLgn")){
                mngIdFromHomeLgn = getArguments().getInt("mngIdFromHomeLgn");
                mngNameFromHomeLgn = getArguments().getString("mngNameFromHomeLgn");
                phoneFromHomeLgn = getArguments().getString("phoneFromHomeLgn");
                buttonAdd.setVisibility(View.GONE);
                ((MainActivity) getActivity()).getSupportActionBar().setTitle("Account's List for ");
                ((MainActivity) getActivity()).getSupportActionBar().setSubtitle("ACCOUNT:- " + mngNameFromHomeLgn);
            }else if (chooser.equals("FromNewPwd")){
                mngIdFromNewPwd = getArguments().getInt("mngIdFromNewPwd");
                mngNameFromNewPwd = getArguments().getString("mngNameFromNewPwd");
                mngPhoneFromNewPwd = getArguments().getString("mngPhoneFromNewPwd");
                buttonAdd.setVisibility(View.GONE);
                ((MainActivity) getActivity()).getSupportActionBar().setTitle("Account's List for ");
                ((MainActivity) getActivity()).getSupportActionBar().setSubtitle("ACCOUNT:- " + mngIdFromNewPwd);
            }
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

        bReceipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle args = new Bundle();
                mngIdFromAccounts= getArguments().getInt("mngIdFromMngs");
                NavHostFragment.findNavController(AccountsFragment.this)
                        .navigate(R.id.action_AccountsFragment_to_AccountDialogFragment, args);
            }
        });

        return v;
    }
    public void refresh(){
        if (chooser.equals("FromMngs")){
            ArrayList<Account> allAccounts = mDatabase.listAccounts(mngIdFromMngs);
            if (allAccounts.size() > 0) {
                accountView.setVisibility(View.VISIBLE);
                AccountAdapter mAdapter = new AccountAdapter(getActivity(), this, allAccounts, getArguments().getString("originPage"));
                accountView.setAdapter(mAdapter);
            }
            else {
                accountView.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "There is no account in the database. Start adding now", Toast.LENGTH_LONG).show();
            }
        }else if (chooser.equals("FromHomeLgn")){
            ArrayList<Account> allAccounts = mDatabase.listAccounts(mngIdFromHomeLgn);
            if (allAccounts.size() > 0) {
                accountView.setVisibility(View.VISIBLE);
                AccountAdapter mAdapter = new AccountAdapter(getActivity(), this, allAccounts, getArguments().getString("originPage"));
                accountView.setAdapter(mAdapter);
            }
            else {
                accountView.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "There is no account in the database. Start adding now", Toast.LENGTH_LONG).show();
            }

        }else if (chooser.equals("FromNewPwd")){
            ArrayList<Account> allAccounts = mDatabase.listAccounts(mngIdFromNewPwd);
            if (allAccounts.size() > 0) {
                accountView.setVisibility(View.VISIBLE);
                AccountAdapter mAdapter = new AccountAdapter(getActivity(), this, allAccounts, getArguments().getString("originPage"));
                accountView.setAdapter(mAdapter);
            }
            else {
                accountView.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "There is no account in the database. Start adding now", Toast.LENGTH_LONG).show();
            }
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
        Bundle args = new Bundle();
        if (chooser.equals("FromMngs")) {
            args.putInt("accIdFromAccs", account.getAccountId());
            args.putString("accNameFromAccs", account.getAccName());
            args.putInt("mngIdFromAccs", mngIdFromMngs);
            args.putString("btnState", "showeButton");
            args.putString("originPage", "FromAccsAdmin");
            NavHostFragment.findNavController(AccountsFragment.this)
                    .navigate(R.id.action_AccountsFragment_to_SubAccountsFragment, args);
        }else if (chooser.equals("FromHomeLgn")) {
            args.putInt("accIdFromAccs", account.getAccountId());
            args.putString("accNameFromAccs", account.getAccName());
            args.putInt("mngIdFromAccs", mngIdFromHomeLgn);
            args.putString("btnState", "showeButton");
            args.putString("originPage", "FromAccsLgn");
            NavHostFragment.findNavController(AccountsFragment.this)
                    .navigate(R.id.action_AccountsFragment_to_SubAccountsFragment, args);
        }else if (chooser.equals("FromNewPwd")) {
            args.putInt("accIdFromAccs", account.getAccountId());
            args.putString("accNameFromAccs", account.getAccName());
            args.putInt("mngIdFromAccs", mngIdFromNewPwd);
            args.putString("btnState", "showeButton");
            args.putString("originPage", "FromAccsPwd");
            NavHostFragment.findNavController(AccountsFragment.this)
                    .navigate(R.id.action_AccountsFragment_to_SubAccountsFragment, args);
        }
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

    private void addTaskDialog() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View subView = inflater.inflate(R.layout.add_accounts, null);
        final EditText nameField = subView.findViewById(R.id.enterAccName);
        final EditText mgidField = subView.findViewById(R.id.enterMgid);
        if (chooser.equals("FromMngs")){
            mgidField.setText(mngIdFromMngs + "");
        }else if (chooser.equals("FromHomeLgn")){
            mgidField.setText(mngIdFromHomeLgn + "");
        }else if (chooser.equals("FromNewPwd")){
            mgidField.setText(mngIdFromNewPwd + "");
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add new ACCOUNT");
        builder.setView(subView);
        builder.create();
        builder.setPositiveButton("ADD ACCOUNT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (chooser.equals("FromMngs")){
                    final String accNme = nameField.getText().toString();
                    final int mngid = mngIdFromMngs;
                    if (TextUtils.isEmpty(accNme)) {
                        Toast.makeText(getActivity(), "Something went wrong. Check your input values", Toast.LENGTH_LONG).show();
                     } else {
                        Account newAccount = new Account(accNme, mngid);
                        mDatabase.addAccounts(newAccount);
                        refresh();
                    }
                }else if (chooser.equals("FromHomeLgn")){
                    final String accNme = nameField.getText().toString();
                    final int mngid = mngIdFromHomeLgn;
                    if (TextUtils.isEmpty(accNme)) {
                        Toast.makeText(getActivity(), "Something went wrong. Check your input values", Toast.LENGTH_LONG).show();
                    } else {
                        Account newAccount = new Account(accNme, mngid);
                        mDatabase.addAccounts(newAccount);
                        refresh();
                    }
                }else if (chooser.equals("FromNewPwd")){
                    final String accNme = nameField.getText().toString();
                    final int mngid = mngIdFromNewPwd;
                    if (TextUtils.isEmpty(accNme)) {
                        Toast.makeText(getActivity(), "Something went wrong. Check your input values", Toast.LENGTH_LONG).show();
                    } else {
                        Account newAccount = new Account(accNme, mngid);
                        mDatabase.addAccounts(newAccount);
                        refresh();
                    }
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