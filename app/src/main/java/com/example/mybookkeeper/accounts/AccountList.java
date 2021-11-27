package com.example.mybookkeeper.accounts;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybookkeeper.R;
import com.example.mybookkeeper.subaccounts.SubAccountList;
import com.example.mybookkeeper.databinding.FragmentAccountListBinding;

import java.util.Arrays;
import java.util.List;

public class AccountList extends Fragment {

    int preSelectedIndex = -1;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    AccountList mCommunication;
    public AccountList() {}// Required empty public constructor

    private List<Account> accounts = Arrays.asList(
            new Account("1", "Core Family", "false"),
            new Account("2", "Murera title", "false"),
            new Account("3", "Rent Collection", "false")
    );

    private FragmentAccountListBinding binding;
    private RecyclerView mRecyclerList = null;
    private AccountAdapter adapter = null;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentAccountListBinding.inflate(inflater, container, false);
        if (preSelectedIndex == -1) {
            preSelectedIndex = 1;
        }else if (preSelectedIndex ==1)
            preSelectedIndex = -1;
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Context context = view.getContext();
        binding.lstAccounts.setLayoutManager(new LinearLayoutManager(context));
        binding.lstAccounts.setAdapter(new AccountAdapter(accounts,
                new DialogInterface() {
                    @Override
                    public void openDialog(Account account) {

                        SubAccountList newFragment = new SubAccountList();
                        Bundle args = new Bundle();
                        args.putString("AccountName", account.getAccountName());
                        newFragment.setArguments(args);
//                        args.putString("AccountID", account.getAccountID());

                        //Toast.makeText(getActivity(), account.getAccountName(),Toast.LENGTH_LONG).show();
                        NavHostFragment.findNavController(AccountList.this)
                                .navigate(R.id.action_AccountList_to_AccountDialog, args);
                    }
                }));
        binding.lstAccounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    public static interface DialogInterface {
        public void openDialog(Account account);
    }

}