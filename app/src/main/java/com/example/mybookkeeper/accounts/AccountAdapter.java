package com.example.mybookkeeper.accounts;

import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mybookkeeper.SqliteDatabase;
import com.example.mybookkeeper.managers.RefreshableFragment;
import com.example.mybookkeeper.R;

import java.util.ArrayList;

class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.AccountViewHolder> implements Filterable {
    private final RefreshableFragment refreshable;
    //private final CheckBoxGroup checkBoxGroup = new CheckBoxGroup();
    private Context context;
    private ArrayList<Account> listAccounts;
    private SqliteDatabase mDatabase;
    String originPage;

    AccountAdapter(Context context, RefreshableFragment refreshable, ArrayList<Account> listAccounts, String originPage) {
        this.context = context;
        this.refreshable = refreshable;
        this.listAccounts = listAccounts;
        this.originPage = originPage;
        mDatabase = new SqliteDatabase(context);
    }

    @Override
    public AccountViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.account_list_layout, parent, false);
        return new AccountViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AccountViewHolder holder, int position) {
        final Account accounts = listAccounts.get(position);
        holder.tvAccName.setText(accounts.getAccName());
        holder.tvMgid.setText(""+accounts.getMgId());
        if (originPage.equalsIgnoreCase("FromMngs")){
            holder.tvMgid.setVisibility(View.VISIBLE);
            holder.editAccount.setVisibility(View.VISIBLE);
            holder.deleteAccount.setVisibility(View.VISIBLE);
        } else if (originPage.equalsIgnoreCase("FromHomeLgn")){
            holder.tvMgid.setVisibility(View.GONE);
            holder.editAccount.setVisibility(View.GONE);
            holder.deleteAccount.setVisibility(View.GONE);
        }else if (originPage.equalsIgnoreCase("FromNewPwd")){
            holder.tvMgid.setVisibility(View.GONE);
            holder.editAccount.setVisibility(View.GONE);
            holder.deleteAccount.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(ll -> {
            refreshable.navigateToSubAccounts(accounts);
        });
        holder.editAccount.setOnClickListener(view -> editTaskDialog(accounts));
        holder.deleteAccount.setOnClickListener(view -> {
            mDatabase.deleteAccount(accounts.getAccountId());
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
                    listAccounts = listAccounts;
                } else {
                    ArrayList<Account> filteredList = new ArrayList<>();
                    for (Account accounts : listAccounts) {
                        if (accounts.getAccName().toLowerCase().contains(charString)) {
                            filteredList.add(accounts);
                        }
                    }
                    listAccounts = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = listAccounts;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listAccounts = (ArrayList<Account>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public int getItemCount() {
        return listAccounts.size();
    }

    private void editTaskDialog(final Account account) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View accView = inflater.inflate(R.layout.add_accounts, null);
        final EditText accNameField = accView.findViewById(R.id.enterAccName);
        final EditText mgIdField = accView.findViewById(R.id.enterMgid);

        if (account != null) {
            accNameField.setText(account.getAccName());
            mgIdField.setText(String.valueOf(account.getMgId()));
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Edit account");
        builder.setView(accView);
        builder.create();
        builder.setPositiveButton("EDIT CONTACT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String accountName = accNameField.getText().toString();
                final int mgId =Integer.parseInt( mgIdField.getText().toString());
                if (TextUtils.isEmpty(accountName) || account == null) {
                    Toast.makeText(context, "Something went wrong. Check your input values", Toast.LENGTH_LONG).show();
                } else {
                    account.setAccName(accountName);
                    account.setMgId(mgId);
                    mDatabase.updateAccounts(account);
                    refreshable.refresh();
                }
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "Task cancelled", Toast.LENGTH_LONG).show();
            }
        });
        builder.show();
    }

    static class AccountViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        TextView tvAccName, tvMgid;
        ImageView deleteAccount;
        ImageView editAccount;

        AccountViewHolder(View itemView) {
            super(itemView);
            tvAccName = itemView.findViewById(R.id.tvAccName);
            tvMgid = itemView.findViewById(R.id.tvMgid);
            deleteAccount = itemView.findViewById(R.id.deleteAccount);
            editAccount = itemView.findViewById(R.id.editAccount);
            //checkBox = itemView.findViewById(R.id.chBoxAccount);
        }
    }
}
