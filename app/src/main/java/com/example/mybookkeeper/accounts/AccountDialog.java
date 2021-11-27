package com.example.mybookkeeper.accounts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mybookkeeper.R;
import com.example.mybookkeeper.subaccounts.SubAccountList;

public class AccountDialog extends DialogFragment {
    private Account account = null;
    //private SubAccount subaccount = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            if (getArguments().containsKey("AccountName")){
                account = new Account(getArguments().getString("AccountID"),
                        getArguments().getString("AccountName"),
                        getArguments().getString("isChecked"));
            }
        }
    }

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_dialog, container, false);

        TextView txtAccountName = view.findViewById(R.id.txtAccount);
        if (account != null){
            txtAccountName.setText(account.getAccountName());
        }

        Button cmdOk = view.findViewById(R.id.cmdOk);
        Button cmdCancel = view.findViewById(R.id.cmdCancel);

        cmdOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SubAccountList newFragment = new SubAccountList();
                Bundle args = new Bundle();
                args.putString("AccountName", txtAccountName.getText().toString());
                newFragment.setArguments(args);

                NavHostFragment.findNavController(AccountDialog.this)
                        .navigate(R.id.action_AccountDialog_to_SubAccountList, args);
            }
        });
/*        cmdOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                NavHostFragment.findNavController(AccountDialog.this)
                        .navigate(R.id.action_AccountDialog_to_SubAccountList, args);
            }
        });*/
        return view;
    }


    private void getSupportActionBar() {
    }
}

