package com.example.mybookkeeper.transactions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.mybookkeeper.R;
import com.example.mybookkeeper.members.MemberList;

public class TransactionFragment extends Fragment {

    public TransactionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = new MemberList().getArguments();
        String value1 = getArguments().getString("MemberName");
        //Toast.makeText(getActivity(),value1,Toast.LENGTH_LONG).show();
        // Set title bar
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle(value1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transactions, container, false);
    }
}