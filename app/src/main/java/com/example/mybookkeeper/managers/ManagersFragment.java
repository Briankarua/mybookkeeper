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
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybookkeeper.MainActivity;
import com.example.mybookkeeper.R;
import com.example.mybookkeeper.SqliteDatabase;
import com.example.mybookkeeper.accounts.Account;
import com.example.mybookkeeper.clients.Client;
import com.example.mybookkeeper.subaccounts.SubAccount;

import java.util.ArrayList;

public class ManagersFragment extends Fragment implements RefreshableFragment {

    private SqliteDatabase mDatabase;
    RecyclerView managerView;
    private FragmentTransaction transaction;
    String admin;
    String chooser;
    Button bAddNew, bReceipt, bExpense;
    String mngNameFromHome;
    int mngIdFromHome;
    String phoneFromHome;
    String managerPw;
    EditText ePhone;
    ManagerAdapter adapter;

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
        Manager manager = null;
        Bundle args = getArguments();

        View v = inflater.inflate(R.layout.fragment_managers, container, false);
        bAddNew = v.findViewById(R.id.btnAdd);
        bReceipt = v.findViewById(R.id.btnRctSumry);
        bExpense = v.findViewById(R.id.btnExpSmry);

        managerView = v.findViewById(R.id.myManagerList);
        ePhone = v.findViewById(R.id.enterPhone);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        managerView.setLayoutManager(linearLayoutManager);
        managerView.setHasFixedSize(true);
        mDatabase = new SqliteDatabase(getActivity());

        if (getArguments() != null){
            mngIdFromHome = getArguments().getInt("mngIdFromHome");
//            mngNameFromHome = getArguments().getString("mngNameFromHome");
//            phoneFromHome = getArguments().getString("phoneFromHome");
//            managerPw = getArguments().getString("Password");
            ((MainActivity) getActivity()).getSupportActionBar().setTitle("Manager: " + mngNameFromHome);
            ((MainActivity) getActivity()).getSupportActionBar().setSubtitle("Phone: " + phoneFromHome+"");
        }else {

            ((MainActivity) getActivity()).getSupportActionBar().setTitle("NO MANAGER SELECTED");
            ((MainActivity) getActivity()).getSupportActionBar().setSubtitle("SELECTED MANAGER NOT FOUND");
        }
        refresh();
        bAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTaskDialog();
            }
        });
        bReceipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
//                args.putInt("mngIDFromManager", mngIdFromHome);
                NavHostFragment.findNavController(ManagersFragment.this)
                        .navigate(R.id.action_ManagersFragment_to_ManagerDialogFragment, args);
            }
        });
        return v;
    }
    public void refresh(){
        ArrayList<Manager> allManagers = mDatabase.listManagers();
        if (allManagers.size() > 0) {
            managerView.setVisibility(View.VISIBLE);
            ManagerAdapter mAdapter = new ManagerAdapter(getActivity(), this, allManagers, chooser);
            managerView.setAdapter(mAdapter);
        }
        else {
            managerView.setVisibility(View.GONE);
            Toast.makeText(getActivity(), "There is no manager in the database. Start adding now", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void navigateToManagers(Manager manager) {
        Bundle args = new Bundle();
        args.putInt("mngIdFromMngs", manager.getManagerID());
        args.putString("mngNameFromMngs", manager.getManagerName());
        args.putString("mngPhoneFromMngs", manager.getManagerPhone());
        args.putString("originPage", "FromMngs");
        args.putString("btnState", "showeButton");
        NavHostFragment.findNavController(com.example.mybookkeeper.managers.ManagersFragment.this)
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
    public void navigateToAccounts(Manager manager) {
        Bundle args = new Bundle();
        args.putInt("mngIdFromMngs", manager.getManagerID());
        args.putString("mngNameFromMngs", manager.getManagerName());
        args.putString("mngPhoneFromMngs", manager.getManagerPhone());
        args.putString("originPage", "FromMngs");
        args.putString("btnState", "showeButton");
        NavHostFragment.findNavController(com.example.mybookkeeper.managers.ManagersFragment.this)
                .navigate(R.id.action_ManagersFragment_to_AccountsFragment, args);
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
                }
                else {
                    Manager newManager = new Manager(mgName, mgPhone, mgPassword);
                    mDatabase.addManagers(newManager);
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