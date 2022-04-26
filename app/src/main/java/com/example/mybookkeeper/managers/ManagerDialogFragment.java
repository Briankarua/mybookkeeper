package com.example.mybookkeeper.managers;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mybookkeeper.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public  class ManagerDialogFragment extends Fragment {

    private TextView tvClient,tvSubaccout;
//    String subAccNameFromClients;
//    String clientNameFromClients;
    int managerIDFromManager;
//    int acntIdFromClients;
//    int subAccIdFromClients;
//    int clientIDFromClients;
    EditText dateFrom, dateTo;

    int mngIdFromMngs;

    public ManagerDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_manager_dialog, container, false);
        Button buttonAddRct = (Button) view.findViewById(R.id.btnAddRct);
        Button btnGetRctSmry = (Button) view.findViewById(R.id.btnRctDetails);
        Button buttonExpDtl = (Button) view.findViewById(R.id.btnExpDetails);
        dateTo = view.findViewById(R.id.edDateTo);

        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        String formattedDate = df.format(date);
        dateFrom = view.findViewById(R.id.edDateFrom);
        dateFrom.setText(formattedDate);
        dateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == dateFrom) {
                    final Calendar calendar = Calendar.getInstance();
                    int mYear = calendar.get(Calendar.YEAR);
                    int mMonth = calendar.get(Calendar.MONTH);
                    int mDay = calendar.get(Calendar.DAY_OF_MONTH);

                    //show dialog
                    DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            calendar.set(year, month, dayOfMonth);
                            SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                            String formattedDate = df.format(calendar.getTime());
                            dateFrom.setText(formattedDate);
                        }
                    }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
            }
        });

        dateTo = view.findViewById(R.id.edDateTo);
        Date date1 = Calendar.getInstance().getTime();
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy/MM/dd");
        String formattedDate1 = df1.format(date1);
        dateTo.setText(formattedDate1);
        dateTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == dateTo) {
                    final Calendar calendar = Calendar.getInstance();
                    int mYear = calendar.get(Calendar.YEAR);
                    int mMonth = calendar.get(Calendar.MONTH);
                    int mDay = calendar.get(Calendar.DAY_OF_MONTH);

                    //show dialog
                    DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            calendar.set(year, month, dayOfMonth);
                            SimpleDateFormat df1 = new SimpleDateFormat("yyyy/MM/dd");
                            String formattedDate1 = df1.format(calendar.getTime());
                            dateTo.setText(formattedDate1);
                        }
                    }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
            }
        });

//        if (getArguments() != null) {
////            mngIdFromMngs = getArguments().getInt("mngIdFromMngs");
////            mngIdFromFromClients = getArguments().getInt("mngIdFromFromClients");
////            acntIdFromClients = getArguments().getInt("acntIdFromClients");
////            subAccIdFromClients = getArguments().getInt("subAccIdFromClients");
////            clientNameFromClients = getArguments().getString("clientNameFromClients");
////            //tvClient.setText(clientNameFromClients);
////            clientIDFromClients = getArguments().getInt("clientIDFromClients");
//            ((MainActivity) getActivity()).getSupportActionBar().setTitle("SUBACC: " + managerIDFromManager);
//            ((MainActivity) getActivity()).getSupportActionBar().setSubtitle("CLIENT: " + managerIDFromManager);
//        }else{
//            ((MainActivity) getActivity()).getSupportActionBar().setTitle("NO ACTIVITY SELECTED");
//        }

        btnGetRctSmry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
//                args.putInt("mngIdFromDialog", mngIdFromMngs);
//                args.putString("subAccNameFromGallety", subAccNameFromClients);
//                args.putInt("acntIdFFromGallety", acntIdFromClients);
//                args.putInt("subAccIdFFromGallety", subAccIdFromClients);
//                args.putString("clientNameFFromGallety", clientNameFromClients);
//                args.putInt("clientIDFFromGallety", clientIDFromClients);
                args.putString("startDate", dateFrom.getText().toString());
                args.putString("endDate", dateTo.getText().toString());
                NavHostFragment.findNavController(ManagerDialogFragment.this)
                        .navigate(R.id.action_ManagerDialogFragment_to_ManagerReceiptsFragment, args);
            }
        });
        buttonExpDtl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
//                args.putString("subAccNameFromGallety", subAccNameFromClients);
//                args.putInt("mngIdFromFFromGallety", mngIdFromFromClients);
//                args.putInt("acntIdFFromGallety", acntIdFromClients);
//                args.putInt("subAccIdFFromGallety", subAccIdFromClients);
//                args.putString("clientNameFFromGallety", clientNameFromClients);
//                args.putInt("clientIDFFromGallety", clientIDFromClients);
                args.putString("startDate", dateFrom.getText().toString());
                args.putString("endDate", dateTo.getText().toString());
//                NavHostFragment.findNavController(com.example.mybookkeeper.fragmernts.TransactionsFragment.this)
//                        .navigate(R.id.action_TransactionsFragment_to_ExpensesDetailsragment, args);
            }
        });
        return view;
    }
}