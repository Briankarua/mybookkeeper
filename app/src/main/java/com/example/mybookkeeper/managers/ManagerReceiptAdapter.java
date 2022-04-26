package com.example.mybookkeeper.managers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mybookkeeper.R;
import com.example.mybookkeeper.SqliteDatabase;

import java.util.ArrayList;

public class ManagerReceiptAdapter<S> extends RecyclerView.Adapter<ManagerReceiptAdapter.ReceiptDataViewHolder>
        implements Filterable {
    private final RefreshableFragment refreshable;
    private ArrayList<ManagerTotal> ManagerReceiptAdapter;
    private  TextView tvDate, tvName, tvAmount;
//    private ImageView editRct, deleteRct;
    private Context context;
    private SqliteDatabase mDatabase;
    int mngId;

    public ManagerReceiptAdapter(Context context, RefreshableFragment refreshable, ArrayList<ManagerTotal> ManagerReceiptAdapter, int mngId) {
        this.context = context;
        this.refreshable = (RefreshableFragment) refreshable;
        this.ManagerReceiptAdapter = ManagerReceiptAdapter;
        this.mngId = mngId;
        mDatabase = new SqliteDatabase(context);
    }

    @Override
    public ReceiptDataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.manager_receipt_layout, parent, false);
        tvDate = view.findViewById(R.id.eDate);
        tvName = view.findViewById(R.id.tvName);
        tvAmount = view.findViewById(R.id.tvAmount);
//        editRct = view.findViewById(R.id.editReceipt);
//        deleteRct = view.findViewById(R.id.deleteReceipt);
        return new ReceiptDataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReceiptDataViewHolder holder, int position) {
        final ManagerTotal managerTotal = ManagerReceiptAdapter.get(position);
//        holder.tvDate.setText(receiptData.getDate());
        holder.tvName.setText(managerTotal.getManager().getManagerName());
        holder.tvAmount.setText("" + managerTotal.getTotal());
//        holder.imgEdit.setOnClickListener(view -> editTaskDialog(receiptData));
//        holder.imgDelete.setOnClickListener(view -> {
//            Toast.makeText(context, "Heloo. delete.", Toast.LENGTH_SHORT).show();
//            mDatabase.deleteReceipt(receiptData.getRctNo());
//            refreshable.refresh();
//        });
    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    ManagerReceiptAdapter = ManagerReceiptAdapter;
                } else {
                    ArrayList<ManagerTotal> filteredList = new ArrayList<>();
                    for (ManagerTotal managerTotal : ManagerReceiptAdapter) {
//                        if (receipts.getPhone().toLowerCase().contains(charString)) {
                            filteredList.add(managerTotal);
//                        }
                    }
                    ManagerReceiptAdapter = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = ManagerReceiptAdapter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                ManagerReceiptAdapter = (ArrayList<ManagerTotal>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public int getItemCount() {
        return ManagerReceiptAdapter.size();
    }

//    private void editTaskDialog(final ReceiptData receipt) {
//        LayoutInflater inflater = LayoutInflater.from(context);
//        View subView = inflater.inflate(R.layout.add_receipt, null);
//        final EditText dateField = subView.findViewById(R.id.enterDate);
//        final EditText rctField = subView.findViewById(R.id.enterRctNo);
//        final EditText subNameField = subView.findViewById(R.id.enterSubName);
//        final EditText mngField = subView.findViewById(R.id.enterMgid);
//        final EditText accField = subView.findViewById(R.id.enteraccId);
//        final EditText subaccField = subView.findViewById(R.id.enterSubId);
//        final EditText clientField = subView.findViewById(R.id.enterCltId);
//        final EditText amtField = subView.findViewById(R.id.enterAmount);
//        //dateField.setEnabled(false);
//        rctField.setEnabled(false);
//        subNameField.setEnabled(false);
//        mngField.setEnabled(false);
//        accField.setEnabled(false);
//        subaccField.setEnabled(false);
//        clientField.setEnabled(false);
//
//        Date date = Calendar.getInstance().getTime();
//        SimpleDateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
//        String formattedDate = df.format(date);
//        dateField.setText(formattedDate);
//        dateField.setFocusable(false);
//        dateField.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (v == dateField) {
//                    final Calendar calendar = Calendar.getInstance();
//                    int mYear = calendar.get(Calendar.YEAR);
//                    int mMonth = calendar.get(Calendar.MONTH);
//                    int mDay = calendar.get(Calendar.DAY_OF_MONTH);
//
//                    //show dialog
//                    DatePickerDialog datePickerDialog = new DatePickerDialog(subView.getContext(), new DatePickerDialog.OnDateSetListener() {
//                        @Override
//                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
////                                dateField.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
//                            calendar.set(year, month, dayOfMonth);
//                            SimpleDateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
//                            String formattedDate = df.format(calendar.getTime());
//                            dateField.setText(formattedDate);
//                        }
//                    }, mYear, mMonth, mDay);
//                    datePickerDialog.show();
//                }
//            }
//        });
//
//        if (receipt != null) {
//            dateField.setText(String.valueOf(receipt.getDate()));
//            rctField.setText("" + receipt.getRctNo());
//            subNameField.setText("" + receipt.getRctNo());
//            mngField.setText("" + receipt.getMgId());
//            accField.setText("" + receipt.getAccId());
//            subaccField.setText("" + receipt.getSubId());
//            clientField.setText("" + receipt.getClientId());
//            amtField.setText("" + receipt.getAmount());
//        }
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setTitle("Edit receipt");
//        builder.setView(subView);
//        builder.create();
//        builder.setPositiveButton("EDIT Receipt", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                final String date = dateField.getText().toString();
//                final int receiptno = Integer.parseInt(rctField.getText().toString());
//                final String subname = rctField.getText().toString();
//                final int mngid = Integer.parseInt(mngField.getText().toString());
//                final int accid = Integer.parseInt(accField.getText().toString());
//                final  int subaccid = Integer.parseInt(subaccField.getText().toString());
//                final  int clntid = Integer.parseInt(clientField.getText().toString());
//                final double amt = Double.parseDouble(amtField.getText().toString());
//                if (TextUtils.isEmpty(receiptno+"") || receiptno+"" == null) {
//                    Toast.makeText(context, "Something went wrong. Check your input values", Toast.LENGTH_LONG).show();
//                } else {
//                    receipt.setDate(date);
//                    receipt.setRctNo(receiptno);
//                    receipt.setSubname(subname);
//                    receipt.setMgId(mngid);
//                    receipt.setAccId(accid);
//                    receipt.setSubId(subaccid);
//                    receipt.setClientId(clntid);
//                    receipt.setSubname(subname);
//                    receipt.setAmount(amt);
//                    mDatabase.updateReceipts(receipt);
//                    refreshable.refresh();
//                }
//            }
//        });
//        builder.show();
//    }

    static class ReceiptDataViewHolder extends RecyclerView.ViewHolder {

        TextView tvDate;
        TextView tvName;
        TextView tvAmount;
//        ImageView imgEdit;
//        ImageView imgDelete;

        ReceiptDataViewHolder(View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvName = itemView.findViewById(R.id.tvName);
            tvAmount = itemView.findViewById(R.id.tvAmount);
        }
    }
}
