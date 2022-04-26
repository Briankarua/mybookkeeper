package com.example.mybookkeeper.accounts;

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
import com.example.mybookkeeper.clients.Client;
import com.example.mybookkeeper.fragmernts.receipts.ReceiptData;
import com.example.mybookkeeper.managers.Manager;
import com.example.mybookkeeper.managers.RefreshableFragment;
import com.example.mybookkeeper.subaccounts.SubAccount;

import java.util.ArrayList;

//import com.example.mybookkeeper.transactions.TransactionActivity;
//import com.example.mybookkeeper.transactions.TransactionsActivity;

public class BlankFragment extends Fragment implements RefreshableFragment {

    private SqliteDatabase mDatabase;
    RecyclerView AccountReceiptView;
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
    int clientIDFFromGallety;
    String startDate, endDate;

    int mngIdFromDialog;
    int acntIdFromDialog;

    int acntIdFromMnggrs;

    public static BlankFragment getInstance(int accId){
        BlankFragment r = new BlankFragment();
        Bundle args = new Bundle();
//        args.putInt("ClientID", clientID);
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

        View v = inflater.inflate(R.layout.fragment_blank, container, false);

        AccountReceiptView = v.findViewById(R.id.myAccountReceiptList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        AccountReceiptView.setLayoutManager(linearLayoutManager);
        AccountReceiptView.setHasFixedSize(true);
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
//            acntIdFromMnggrs= getArguments().getInt("acntIdFromMnggrs");
            startDate = getArguments().getString("startDate");
            endDate = getArguments().getString("endDate");
            ((MainActivity) getActivity()).getSupportActionBar().setTitle("Receipt Details for ");
            ((MainActivity) getActivity()).getSupportActionBar().setSubtitle("CLIENT:- " + acntIdFromMnggrs);

        }else{
            ((MainActivity) getActivity()).getSupportActionBar().setTitle("NO ACCOUNT SELECTED");
            ((MainActivity) getActivity()).getSupportActionBar().setSubtitle("SELECTED ACCOUNT NOT FOUND");
        }
//        refresh();
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
        //Toast.makeText(getActivity(), ""+mngIdFromDialog, Toast.LENGTH_LONG).show();
        ArrayList<AccountTotal> allReceipts = mDatabase.listAccTotalReceipts(startDate, endDate);
        if (allReceipts.size() > 0) {
            AccountReceiptView.setVisibility(View.VISIBLE);
            AccountReceiptAdapter mAdapter = new AccountReceiptAdapter(getActivity(),  this, getArguments().getInt("acntIdFromDialog"));
            AccountReceiptView.setAdapter(mAdapter);
        }
        else {
            AccountReceiptView.setVisibility(View.GONE);
            Toast.makeText(getActivity(), "There is no account in the database. Start adding now", Toast.LENGTH_LONG).show();
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
    public void onDestroy() {
        super.onDestroy();
        if (mDatabase != null) {
            mDatabase.close();
        }
    }
}