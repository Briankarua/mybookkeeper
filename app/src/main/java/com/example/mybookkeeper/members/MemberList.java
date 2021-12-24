package com.example.mybookkeeper.members;

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
import com.example.mybookkeeper.accounts.AccountsFragment;
import com.example.mybookkeeper.transactions.TransactionActivity;
import com.example.mybookkeeper.databinding.FragmentMemberListBinding;

import java.util.Arrays;
import java.util.List;

public class MemberList extends Fragment {
    int preSelectedIndex = -1;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    AccountsFragment mCommunication;
    public MemberList() {}// Required empty public constructor

    private List<Member> member = Arrays.asList(
            new Member("33", "Kamama Wetan", "false"),
            new Member("44", "Miusu Susu", "false"),
            new Member("456", "Quante Econo", "false")
    );

    private FragmentMemberListBinding binding;
    private RecyclerView mRecyclerList = null;
    private MemberAdapter adapter = null;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentMemberListBinding.inflate(inflater, container, false);
        if (preSelectedIndex == -1) {
            preSelectedIndex = 1;
        }else if (preSelectedIndex ==1)
            preSelectedIndex = -1;
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Context context = view.getContext();
        binding.lstMember.setLayoutManager(new LinearLayoutManager(context));
        binding.lstMember.setAdapter(new MemberAdapter(member,
                new DialogInterface() {
                    @Override
                    public void openDialog(Member member) {

                        TransactionActivity newFragment = new TransactionActivity();
                        Bundle args = new Bundle();
                        args.putString("MemberName", member.getMemberName());
                        NavHostFragment.findNavController(MemberList.this)
                                .navigate(R.id.action_MemberList_to_MemberDialog, args);

                    }
                }));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    public static interface DialogInterface {
        public void openDialog(Member member);
    }
}