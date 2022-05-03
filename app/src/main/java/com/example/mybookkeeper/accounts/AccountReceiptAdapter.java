package com.example.mybookkeeper.accounts;

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
import com.example.mybookkeeper.fragmernts.receipts.ReceiptData;
import com.example.mybookkeeper.uiutils.RefreshableFragment;

import java.util.ArrayList;

public class AccountReceiptAdapter<S> extends RecyclerView.Adapter<AccountReceiptAdapter.ReceiptDataViewHolder>
        implements Filterable {
    private final RefreshableFragment refreshable;
    private ArrayList<ReceiptData> AccountReceiptAdapter;
    private  TextView tvDate, tvName, tvAmount;
    //    private ImageView editRct, deleteRct;
    private Context context;
    private SqliteDatabase mDatabase;
    int accId;

    public AccountReceiptAdapter(Context context, RefreshableFragment refreshable, int accId) {
        this.context = context;
        this.refreshable = (RefreshableFragment) refreshable;
        this.AccountReceiptAdapter = AccountReceiptAdapter;
        this.accId = accId;
        mDatabase = new SqliteDatabase(context);
    }

    @Override
    public AccountReceiptAdapter.ReceiptDataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.manager_receipt_layout, parent, false);
        tvDate = view.findViewById(R.id.eDate);
        tvName = view.findViewById(R.id.tvName);
        tvAmount = view.findViewById(R.id.tvAmount);
//        editRct = view.findViewById(R.id.editReceipt);
//        deleteRct = view.findViewById(R.id.deleteReceipt);
        return new AccountReceiptAdapter.ReceiptDataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AccountReceiptAdapter.ReceiptDataViewHolder holder, int position) {
        final ReceiptData receiptData = AccountReceiptAdapter.get(position);
        holder.tvDate.setText(receiptData.getDate());
        holder.tvName.setText(receiptData.getMngName());
        holder.tvAmount.setText("" + receiptData.getAmount());
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
                    AccountReceiptAdapter = AccountReceiptAdapter;
                } else {
                    ArrayList<ReceiptData> filteredList = new ArrayList<>();
                    for (ReceiptData receipts : AccountReceiptAdapter) {
//                        if (receipts.getPhone().toLowerCase().contains(charString)) {
//                            filteredList.add(receipts);
//                        }
                    }
                    AccountReceiptAdapter = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = AccountReceiptAdapter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                AccountReceiptAdapter = (ArrayList<ReceiptData>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public int getItemCount() {
        return AccountReceiptAdapter.size();
    }

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

