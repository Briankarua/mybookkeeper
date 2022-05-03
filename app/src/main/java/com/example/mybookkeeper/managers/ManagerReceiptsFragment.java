package com.example.mybookkeeper.managers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybookkeeper.MainActivity;
import com.example.mybookkeeper.R;
import com.example.mybookkeeper.SqliteDatabase;
import com.example.mybookkeeper.accounts.Account;
import com.example.mybookkeeper.clients.Client;
import com.example.mybookkeeper.fragmernts.receipts.ReceiptData;
import com.example.mybookkeeper.subaccounts.SubAccount;
import com.example.mybookkeeper.uiutils.RefreshableFragment;
import com.example.mybookkeeper.uiutils.RefreshableNavigatable;

import java.util.ArrayList;

//import com.example.mybookkeeper.transactions.TransactionActivity;
//import com.example.mybookkeeper.transactions.TransactionsActivity;

public class ManagerReceiptsFragment extends Fragment implements RefreshableNavigatable {

    private SqliteDatabase mDatabase;
    RecyclerView ReceiptManagerView;
    ReceiptData ReceiptData;
    EditText eRctNo, eDate, eSubName, eMgclid, eAccId, eSubaccId, eClientId, eAmount;
    int accId;
    String fromAcc;
    String clientName;

    String subAccNameFromGallety;;
    int mngIdFromFFromGallety;
    int acntIdFFromGallety;
    int subAccIdFFromGallety;
    String clientNameFFromGallety;
    int mngIdFFromGallety;
    String startDate, endDate;

    int mngIdFromDialog;

    public static ManagerReceiptsFragment getInstance(int mngId){
        ManagerReceiptsFragment r = new ManagerReceiptsFragment();
        Bundle args = new Bundle();
        r.setArguments(args);
        return r;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ReceiptData ReceiptData = null;
        Bundle args = getArguments();

        View v = inflater.inflate(R.layout.fragment_manager_receipts, container, false);

        ReceiptManagerView = v.findViewById(R.id.myReceiptManagerList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        ReceiptManagerView.setLayoutManager(linearLayoutManager);
        ReceiptManagerView.setHasFixedSize(true);
        mDatabase = new SqliteDatabase(getActivity());
        eRctNo = v.findViewById(R.id.eRctNo);
        eSubName = v.findViewById(R.id.eSubName);
        eMgclid = v.findViewById(R.id.eMgclid);
        eDate = v.findViewById(R.id.eDate);
        eAccId = v.findViewById(R.id.eAccId);
        eSubaccId = v.findViewById(R.id.eSubaccId);
        eClientId = v.findViewById(R.id.eClient);
        eAmount = v.findViewById(R.id.eAmount);

        if (getArguments() != null) {
//            subAccNameFromGallety = getArguments().getString("subAccNameFromGallety");
            mngIdFromDialog = getArguments().getInt("mngIdFromDialog");
//            acntIdFFromGallety = getArguments().getInt("acntIdFFromGallety");
//            subAccIdFFromGallety = getArguments().getInt("subAccIdFFromGallety");
//            clientNameFFromGallety = getArguments().getString("clientNameFFromGallety");
//            clientIDFFromGallety = getArguments().getInt("clientIDFFromGallety");
            startDate = getArguments().getString("startDate");
            endDate = getArguments().getString("endDate");
            ((MainActivity) getActivity()).getSupportActionBar().setTitle("Receipt Details for ");
            ((MainActivity) getActivity()).getSupportActionBar().setSubtitle("CLIENT:- " + mngIdFromDialog);

        }else{
            ((MainActivity) getActivity()).getSupportActionBar().setTitle("NO ACCOUNT SELECTED");
            ((MainActivity) getActivity()).getSupportActionBar().setSubtitle("SELECTED ACCOUNT NOT FOUND");
        }
        refresh();
//        Button btnAdd = v.findViewById(R.id.btnAdd);
//        btnAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                addTaskDialog();
//            }
//        });
        return v;
    }
    public void refresh(){
        ArrayList<ManagerTotal> allReceipts = mDatabase.listMngrTotalReceipts(startDate, endDate);
        if (allReceipts.size() > 0) {
            ReceiptManagerView.setVisibility(View.VISIBLE);
            ManagerReceiptAdapter mAdapter = new ManagerReceiptAdapter(getActivity(),  this, allReceipts, getArguments().getInt("mngIdFromDialog"));
            ReceiptManagerView.setAdapter(mAdapter);
        }
        else {
            ReceiptManagerView.setVisibility(View.GONE);
            Toast.makeText(getActivity(), "There is no account in the database. Start adding now", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void navigateToAccounts(Manager manager) {

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

    }

    @Override
    public void navigateToAccountReceiptDialog(Account account) {

    }

    @Override
    public void navigateToAccountReceiptDFragment(Account account) {

    }

    //    private void addTaskDialog() {
//        LayoutInflater inflater = LayoutInflater.from(getActivity());
//        View subView = inflater.inflate(R.layout.receipt_list_layout, null);
//        final EditText dateField = subView.findViewById(R.id.eDate);
//        final EditText rctnoField = subView.findViewById(R.id.eRctNo);
//        final EditText subField = subView.findViewById(R.id.eSubName);
//        final EditText mngField = subView.findViewById(R.id.eMgclid);
//        final EditText accField = subView.findViewById(R.id.eAccId);
//        final EditText subaccField = subView.findViewById(R.id.eSubaccId);
//        final EditText clientField = subView.findViewById(R.id.eClient);
//        final EditText amountField = subView.findViewById(R.id.eAmount);
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setTitle("Add new Receipt");
//        builder.setView(subView);
//        builder.create();
//        builder.setPositiveButton("ADD Receipt", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                ReceiptData = mDatabase.searchReceiptByID(Integer.parseInt(mngField.getText().toString()));
//                final String date = dateField.getText().toString();
//                final int rctNo = Integer.parseInt(ReceiptData.getRctNo()+"");
//                final String subname = subField.getText().toString();
//                final int mngId = Integer.parseInt(ReceiptData.getMgId()+"");
//                final int accId = Integer.parseInt(ReceiptData.getAccId()+"");
//                final int subaccId = Integer.parseInt(ReceiptData.getSubId()+"");
//                final int clientId = Integer.parseInt(ReceiptData.getClientId()+"");
//                final double amount = Double.parseDouble(amountField.getText().toString());
//
//                if (!TextUtils.isEmpty("" + amount)) {
//                    ReceiptData newReceipt = new ReceiptData(date, rctNo, subname, mngId, accId, subaccId, clientId, amount);
//                    mDatabase.addReceipt(newReceipt);
//                    refresh();
//                } else {
//                    Toast.makeText(getActivity(), "Something went wrong. Check your input values", Toast.LENGTH_LONG).show();
//                }
//            }
//        });
//        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(getActivity(), "Task cancelled", Toast.LENGTH_LONG).show();
//            }
//        });
//        builder.show();
//    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mDatabase != null) {
            mDatabase.close();
        }
    }

}