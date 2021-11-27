package com.example.mybookkeeper.subaccounts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mybookkeeper.R;
import com.example.mybookkeeper.members.MemberList;

public class SubAccountDialog extends DialogFragment {
    private SubAccount subAccount = null;
    //private MemberAccount memberAccount = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            if (getArguments().containsKey("SubAccountName")){
                subAccount = new SubAccount(getArguments().getString("SubAccountID"),
                        getArguments().getString("SubAccountName"),
                        getArguments().getString("isSubChecked"));
            }
        }
    }

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subaccount_dialog, container, false);

        TextView txtSubAccountName = view.findViewById(R.id.txtSubAccount);

        if (subAccount != null){
            txtSubAccountName.setText(subAccount.getSubAccountName());
        }

        Button cmdOk = view.findViewById(R.id.cmdOk);
        Button cmdCancel = view.findViewById(R.id.cmdCancel);

        cmdOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MemberList newFragment = new MemberList();
                Bundle args = new Bundle();
                args.putString("SubAccountName", txtSubAccountName.getText().toString());
                newFragment.setArguments(args);

                NavHostFragment.findNavController(SubAccountDialog.this)
                        .navigate(R.id.action_SubAccountDialog_to_MemberList, args);
            }
        });
        return view;
    }
}

