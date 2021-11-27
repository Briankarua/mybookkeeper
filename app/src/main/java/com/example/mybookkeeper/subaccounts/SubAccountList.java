package com.example.mybookkeeper.subaccounts;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.mybookkeeper.R;
import com.example.mybookkeeper.accounts.AccountList;
import com.example.mybookkeeper.databinding.FragmentSubAccountListBinding;
import com.example.mybookkeeper.members.MemberList;

import java.util.Arrays;
import java.util.List;

public class SubAccountList extends Fragment {

    private List<SubAccount> subaccounts = Arrays.asList(
            new SubAccount("1", "Gas", "false"),
            new SubAccount("2", "Electricity", "false"),
            new SubAccount("3", "Repairs", "false")
    );

    private FragmentSubAccountListBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        Bundle bundle = new AccountList().getArguments();
        String value1 = getArguments().getString("AccountName");
        Toast.makeText(getActivity(),value1,Toast.LENGTH_LONG).show();
        // Set title bar
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle(value1);

        binding = FragmentSubAccountListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    private void setActionBarTitle(String your_title) {

    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Context context = view.getContext();
        binding.lstSubAccounts.setLayoutManager(new LinearLayoutManager(context));
        binding.lstSubAccounts.setAdapter(new SubAccountAdapter(subaccounts, new DialogInterface() {
            @Override
            public void openDialog(SubAccount subaccount) {

                MemberList newFragment = new MemberList();
                Bundle args = new Bundle();
                args.putString("SubAccountName", subaccount.getSubAccountName());
                newFragment.setArguments(args);
//                        args.putString("AccountID", account.getAccountID());

                NavHostFragment.findNavController(SubAccountList.this)
                        .navigate(R.id.action_SubAccountList_to_SubAccountDialog, args);
            }
        }));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    public static interface DialogInterface {
        public void openDialog(SubAccount subaccount);

    }
}