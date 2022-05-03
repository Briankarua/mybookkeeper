package com.example.mybookkeeper.managers;

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
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybookkeeper.MainActivity;
import com.example.mybookkeeper.R;
import com.example.mybookkeeper.accounts.Account;
import com.example.mybookkeeper.clients.Client;
import com.example.mybookkeeper.managers.models.ManagersViewModel;
import com.example.mybookkeeper.managers.models.ManagersViewModelFactory;
import com.example.mybookkeeper.subaccounts.SubAccount;
import com.example.mybookkeeper.uiutils.CustomNavigation;

public class ManagersFragment2 extends Fragment implements CustomNavigation {

    RecyclerView managerView;
    Button bAddNew, bReceipt, bExpense;
    String mngNameFromHome;
    int mngIdFromHome;
    String phoneFromHome;
    EditText ePhone;
    ManagersViewModel viewModel;

    public ManagersFragment2() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_managers, container, false);
        bAddNew = v.findViewById(R.id.btnAdd);
        bReceipt = v.findViewById(R.id.btnRctSumry);
        bExpense = v.findViewById(R.id.btnExpSmry);

        managerView = v.findViewById(R.id.myManagerList);
        ePhone = v.findViewById(R.id.enterPhone);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        managerView.setLayoutManager(linearLayoutManager);
        managerView.setHasFixedSize(true);
        ActionBar supportActionBar = ((MainActivity) requireActivity()).getSupportActionBar();
        supportActionBar.show();
        if (getArguments() != null) {
            mngIdFromHome = getArguments().getInt("mngIdFromHome");
            supportActionBar.setTitle("Manager: " + mngNameFromHome);
            supportActionBar.setSubtitle("Phone: " + phoneFromHome + "");
        } else {

            supportActionBar.setTitle("NO MANAGER SELECTED");
            supportActionBar.setSubtitle("SELECTED MANAGER NOT FOUND");
        }

        bReceipt.setOnClickListener(view -> {
            Bundle args1 = new Bundle();
            NavHostFragment.findNavController(ManagersFragment2.this)
                    .navigate(R.id.action_ManagersFragment_to_ManagerDialogFragment, args1);
        });


        viewModel = new ViewModelProvider(this).get(ManagersViewModel.class);

        bAddNew.setOnClickListener(view -> addTaskDialog());
        int i = viewModel.totalManagers();
        if (i > 0) {
            ManagerAdapter2 mAdapter = new ManagerAdapter2(new ManagerAdapter2.ManagerComparator(),
                    getActivity(), this);
            managerView.setVisibility(View.VISIBLE);
            managerView.setAdapter(mAdapter);

            viewModel.allManagers()
                    // Using AutoDispose to handle subscription lifecycle.
                    // See: https://github.com/uber/AutoDispose
                    .subscribe(pagingData -> mAdapter.submitData(getLifecycle(), pagingData));
        } else {
            managerView.setVisibility(View.GONE);
            Toast.makeText(getActivity(), "There is no manager in the database. Start adding now", Toast.LENGTH_LONG).show();
        }
        return v;
    }

    @Override
    public void navigateToAccounts(Manager manager) {
        Bundle args = new Bundle();

        args.putInt("manager_id", manager.getManagerID());
        args.putString("manager_name", manager.getManagerName());
        args.putString("manager_phone", manager.getManagerPhone());
        args.putDouble("manager_receipts_total", manager.getTotalReceipts());
        args.putDouble("manager_expenses_total", manager.getTotalExpenses());
        args.putDouble("manager_balances_total", manager.getTotalBalances());
        args.putString("originPage", "FromMngs");
        args.putBoolean("show_add_button", true);
        NavHostFragment.findNavController(ManagersFragment2.this)
                .navigate(R.id.action_ManagersFragment_to_AccountsFragment, args);
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
    public void navigateToSubAccounts(Account account) {

    }

    @Override
    public void navigateToClientsDialog(Client client) {

    }

    @Override
    public void navigateToManagerDialog(Manager manager) {
//        Bundle args = new Bundle();
//        args.putInt("clientIDFromClients", client.getId());
//        args.putString("clientNameFromClients", client.getCltName());
//        args.putInt("subAccIdFromClients", subAccIdFromSubacc);
//        args.putString("subAccNameFromClients", subAccNameFromSubaccs);
//        args.putInt("acntIdFromClients", acntIdFromSubaccs);
//        args.putInt("mngIdFromFromClients", mngIdFromSubacc);
//        NavHostFragment.findNavController(ManagersFragment.this)
//                .navigate(R.id.action_ManagersFragment_to_ManagerDialogFragment, args);
    }

    @Override
    public void navigateToAccountReceiptDialog(Account account) {

    }

    @Override
    public void navigateToAccountReceiptDFragment(Account account) {

    }


    private void addTaskDialog() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View mgView = inflater.inflate(R.layout.add_managers, null);
        final EditText nameField = mgView.findViewById(R.id.enterName);
        final EditText phoneField = mgView.findViewById(R.id.enterPhone);
        final EditText passwordField = mgView.findViewById(R.id.enterPword);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add new MANAGEER");
        builder.setView(mgView);
        builder.create();
        builder.setPositiveButton("ADD MANAGEER", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String mgName = nameField.getText().toString();
                final String mgPhone = phoneField.getText().toString();
                final String mgPassword = passwordField.getText().toString();
                if (TextUtils.isEmpty(mgName)) {
                    Toast.makeText(getActivity(), "Something went wrong. Check your input values", Toast.LENGTH_LONG).show();
                } else {
                    Manager newManager = new Manager(mgName, mgPhone, mgPassword);
                    viewModel.create(newManager);
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
    }

    @NonNull
    @Override
    public ViewModelProvider.Factory getDefaultViewModelProviderFactory() {
        return new ManagersViewModelFactory(getActivity().getApplication());
    }
}