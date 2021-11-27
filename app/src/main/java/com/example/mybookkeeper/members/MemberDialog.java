package com.example.mybookkeeper.members;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.example.mybookkeeper.R;
import com.example.mybookkeeper.transactions.TransactionActivity;


public class MemberDialog extends DialogFragment {
    private Member member = null;
    private RadioGroup radioSexGroup;
    private Button btnSubmit;
    private TextView txtTransaction;
    public  String keys;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null){
            if (getArguments().containsKey("MemberName")){
                member = new Member(getArguments().getString("MemberID"),
                        getArguments().getString("MemberName"),
                        getArguments().getString("isMemChecked"));
            }
        }
    }

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_member_dialog, container, false);

        TextView txtMemberName = view.findViewById(R.id.txtMember);
        if (member != null){
            txtMemberName.setText(member.getMemberName());
        }

        radioSexGroup = (RadioGroup) view.findViewById(R.id.radioSex);
        btnSubmit = (Button) view.findViewById(R.id.goBtn);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                                // get selected radio button from radioGroup
                int selectedId = radioSexGroup.getCheckedRadioButtonId();
                // find the radiobutton by returned id
                RadioButton radioTransactionButton = (RadioButton) view.findViewById(selectedId);

                //Toast.makeText(view.getContext(), radioTransactionButton.getText().toString(),Toast.LENGTH_LONG).show();

/*                // get selected radio button from radioGroup
                int selectedId = radioSexGroup.getCheckedRadioButtonId();
                // find the radiobutton by returned id
                RadioButton radioTransactionButton = (RadioButton) view.findViewById(selectedId);

                Intent intent  = new Intent(getActivity(), TransactionActivity.class);
                intent.putExtra("FRAGMENT_ID", 2);
                startActivity(intent);
*//*                Intent intent = new Intent(getActivity(), TransactionActivity.class);
                intent.putExtra("Add", radioTransactionButton.getText().toString());
                intent.putExtra("Sub", txtMemberName.getText().toString());
                startActivity(intent);*/
                int page = 0;
                if (radioTransactionButton.getText().toString().equals("Add Receipt")){
                    page = 0;
                } else if (radioTransactionButton.getText().toString().equals("Add Expense")){
                    page = 1;
                }else if (radioTransactionButton.getText().toString().equals("Get Summary")){
                    page = 2;
                }

                Intent intent = new Intent(getActivity(), TransactionActivity.class);
                intent.putExtra("One", page);// One is your argument
                startActivity(intent);

            }
        });
        return view;
    }
}