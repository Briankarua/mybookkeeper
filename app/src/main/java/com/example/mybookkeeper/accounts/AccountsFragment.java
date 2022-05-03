package com.example.mybookkeeper.accounts;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybookkeeper.MainActivity;
import com.example.mybookkeeper.R;
import com.example.mybookkeeper.SqliteDatabase;
import com.example.mybookkeeper.clients.Client;
import com.example.mybookkeeper.managers.Manager;
import com.example.mybookkeeper.subaccounts.SubAccount;
import com.example.mybookkeeper.uiutils.RefreshableNavigatable;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;
import java.util.Objects;

public class AccountsFragment extends Fragment implements RefreshableNavigatable {

    private SqliteDatabase mDatabase;
    private RecyclerView accountView;
    private Button buttonAdd, bReceipt, bExpense;
    private EditText eMgid;

    private String managerName, managerPhone;
    private int managerId;
    private double managerReceiptsTotal, managerExpensesTotal, managerBalancesTotal;

    private CollapsingToolbarLayout collapsingToolbar;

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

        View v = inflater.inflate(R.layout.fragment_accounts_2, container, false);
        buttonAdd = v.findViewById(R.id.btnAdd);
        bReceipt = v.findViewById(R.id.btnRctSumry);
        bExpense = v.findViewById(R.id.btnExpSmry);
        accountView = v.findViewById(R.id.myAccountList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        accountView.setLayoutManager(linearLayoutManager);
        accountView.setHasFixedSize(true);
        eMgid = v.findViewById(R.id.enterMgid);
        collapsingToolbar = v.findViewById(R.id.custom_toolbar_layout);
        mDatabase = new SqliteDatabase(getActivity());
        Toolbar toolbar = v.findViewById(R.id.toolbar_red);
        ActionBar supportActionBar = ((MainActivity) requireActivity()).getSupportActionBar();
        if (getArguments() != null) {
            managerId = getArguments().getInt("manager_id");
            managerName = getArguments().getString("manager_name");
            managerPhone = getArguments().getString("manager_phone");
            managerReceiptsTotal = getArguments().getDouble("manager_receipts_total", 0);
            managerExpensesTotal = getArguments().getDouble("manager_expenses_total", 0);
            managerBalancesTotal = getArguments().getDouble("manager_balances_total", 0);
            boolean showAddButton = getArguments().getBoolean("show_add_button", false);
            if (showAddButton) {
                buttonAdd.setVisibility(View.VISIBLE);
            } else {
                buttonAdd.setVisibility(View.GONE);
            }

            ImageView imageView = v.findViewById(R.id.managerProfileImg);
            Drawable profileImg = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_baseline_person_60, null);
            imageView.setImageDrawable(profileImg);

            supportActionBar.hide();
            toolbar.setTitle(managerName);

            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
            currencyFormat.setCurrency(Currency.getInstance("KES"));
            TextView receiptsTotalTxt = v.findViewById(R.id.receiptsTextView);
            String formatedRcpts = currencyFormat.format(managerReceiptsTotal);
            receiptsTotalTxt.setText(formatedRcpts);

            TextView balancesTotalTxt = v.findViewById(R.id.balancesTextView);
            String formatedBalances = currencyFormat.format(managerBalancesTotal);
            balancesTotalTxt.setText(formatedBalances);

            TextView expensesTotalTxt = v.findViewById(R.id.expensesTextView);
            String formatedExpenses = currencyFormat.format(managerExpensesTotal);
            expensesTotalTxt.setText(formatedExpenses);

            TextView managerIdTxt = v.findViewById(R.id.managerIdView);
            String formatedId = String.format(requireContext().getString(R.string.manager_id_str_msg), managerId);
            managerIdTxt.setText(formatedId);

        } else {
            supportActionBar.show();
            supportActionBar.setTitle("NO ACCOUNT SELECTED");
            supportActionBar.setSubtitle("SELECTED ACCOUNT NOT FOUND");
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
                NavHostFragment.findNavController(AccountsFragment.this)
                        .navigate(R.id.action_AccountsFragment_to_AccountDialogFragment, args);
            }
        });

        return v;
    }

    public void refresh() {
        ArrayList<Account> accounts = mDatabase.listAccounts(managerId);
        if (accounts.size() > 0) {
            accountView.setVisibility(View.VISIBLE);
            AccountAdapter mAdapter = new AccountAdapter(getActivity(), this, accounts, getArguments().getString("originPage"));
            accountView.setAdapter(mAdapter);
        } else {
            accountView.setVisibility(View.GONE);
            Toast.makeText(getActivity(), "There is no account in the database. Start adding now", Toast.LENGTH_LONG).show();
        }
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
        args.putInt("account_id", account.getAccountId());
        args.putString("account_name", account.getAccName());
        args.putInt("manager_id", managerId);
        args.putBoolean("show_add_button", true);
        args.putString("originPage", "FromAccsAdmin");
        NavHostFragment.findNavController(AccountsFragment.this)
                .navigate(R.id.action_AccountsFragment_to_SubAccountsFragment, args);
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
        mgidField.setText(String.format("%d", managerId));
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add new ACCOUNT");
        builder.setView(subView);
        builder.create();
        builder.setPositiveButton("ADD ACCOUNT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                final String accNme = nameField.getText().toString();
                final int mngid = managerId;
                if (TextUtils.isEmpty(accNme)) {
                    Toast.makeText(getActivity(), "Something went wrong. Check your input values", Toast.LENGTH_LONG).show();
                } else {
                    Account newAccount = new Account(accNme, mngid);
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