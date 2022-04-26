package com.example.mybookkeeper.subaccounts;

import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybookkeeper.R;
import com.example.mybookkeeper.SqliteDatabase;
import com.example.mybookkeeper.managers.RefreshableFragment;

import java.util.ArrayList;

public class SubAccountAdapter extends RecyclerView.Adapter<SubAccountAdapter.SubAccountViewHolder>
        implements Filterable {
    private final RefreshableFragment refreshable;
    private Context context;
    private ArrayList<SubAccount> listSubAccounts;
    private SqliteDatabase mDatabase;
    String originPage;

    SubAccountAdapter(Context context, RefreshableFragment refreshable, ArrayList<SubAccount> listSubAccounts, String originPage) {
        this.context = context;
        this.refreshable = refreshable;
        this.listSubAccounts = listSubAccounts;
        this.originPage = originPage;
        mDatabase = new SqliteDatabase(context);
    }

    @Override
    public SubAccountViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subaccount_list_layout, parent, false);
        return new SubAccountViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SubAccountViewHolder holder, int position) {
        final SubAccount subaccounts = listSubAccounts.get(position);
        holder.tvName.setText(subaccounts.getSubAccName());
        holder.tvSubMgid.setText("" + subaccounts.getSubMgId());
        holder.tvAccId.setText("" + subaccounts.getAccId());
        if (originPage.equalsIgnoreCase("FromAccsAdmin")){
            holder.tvSubMgid.setVisibility(View.VISIBLE);
            holder.tvAccId.setVisibility(View.VISIBLE);
            holder.editSubAccount.setVisibility(View.VISIBLE);
            holder.deleteSubAccount.setVisibility(View.VISIBLE);
        } else if (originPage.equalsIgnoreCase("FromAccsLgn")){
            holder.tvSubMgid.setVisibility(View.GONE);
            holder.tvAccId.setVisibility(View.GONE);
            holder.editSubAccount.setVisibility(View.GONE);
            holder.deleteSubAccount.setVisibility(View.GONE);
        } else if (originPage.equalsIgnoreCase("FromAccsPwd")){
            holder.tvSubMgid.setVisibility(View.GONE);
            holder.tvAccId.setVisibility(View.GONE);
            holder.editSubAccount.setVisibility(View.GONE);
            holder.deleteSubAccount.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(ll -> {
            refreshable.navigateToClients(subaccounts);
        });
        holder.editSubAccount.setOnClickListener(view -> editTaskDialog(subaccounts));
        holder.deleteSubAccount.setOnClickListener(view -> {
            Toast.makeText(context, "Heloo. delete.", Toast.LENGTH_SHORT).show();
            mDatabase.deleteSubAccount(subaccounts.getSubAccountId());
            refreshable.refresh();
        });
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    listSubAccounts = listSubAccounts;
                } else {
                    ArrayList<SubAccount> filteredList = new ArrayList<>();
                    for (SubAccount subaccounts : listSubAccounts) {
                        if (subaccounts.getSubAccName().toLowerCase().contains(charString)) {
                            filteredList.add(subaccounts);
                        }
                    }
                    listSubAccounts = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = listSubAccounts;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listSubAccounts = (ArrayList<SubAccount>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public int getItemCount() {
        return listSubAccounts.size();
    }

    private void editTaskDialog(final SubAccount subaccount) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View subView = inflater.inflate(R.layout.add_subaccount, null);
        final EditText nameField = subView.findViewById(R.id.entersacName);
        final EditText mgidField = subView.findViewById(R.id.entersacMgId);
        final EditText accIdField = subView.findViewById(R.id.entersacAccId);
        if (subaccount != null) {
            nameField.setText(subaccount.getSubAccName());
            mgidField.setText(subaccount.getSubMgId() + "");
            accIdField.setText(String.valueOf(subaccount.getAccId()));
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Edit subaccount");
        builder.setView(subView);
        builder.create();
        builder.setPositiveButton("EDIT SUBACCOUNT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String name = nameField.getText().toString();
                final int mgid = Integer.parseInt(mgidField.getText().toString());
                final int accId = Integer.parseInt(accIdField.getText().toString());
                if (TextUtils.isEmpty(name) || subaccount == null) {
                    Toast.makeText(context, "Something went wrong. Check your input values", Toast.LENGTH_LONG).show();
                } else {
                    subaccount.setSubAccountName(name);
                    subaccount.setSubMgId(mgid);
                    subaccount.setSubAccId(accId);
                    mDatabase.updateSubAccount(subaccount);
                    refreshable.refresh();
                }
            }
        }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "Task cancelled", Toast.LENGTH_LONG).show();
            }
        });
        builder.show();
    }

    static class SubAccountViewHolder extends RecyclerView.ViewHolder {
        //CheckBox checkBox;
        TextView tvName, tvSubMgid, tvAccId;
        ImageView deleteSubAccount;
        ImageView editSubAccount;

        SubAccountViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvsubName);
            tvSubMgid = itemView.findViewById(R.id.tvSubMgid);
            tvAccId = itemView.findViewById(R.id.tvAccId);
            deleteSubAccount = itemView.findViewById(R.id.deleteSubAccount);
            editSubAccount = itemView.findViewById(R.id.editSubAccount);
            //checkBox = itemView.findViewById(R.id.chBoxSubAccount);
        }
    }
}