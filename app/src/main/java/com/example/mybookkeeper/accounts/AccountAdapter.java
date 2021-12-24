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

import com.example.mybookkeeper.CheckBoxGroup;
import com.example.mybookkeeper.R;
import com.example.mybookkeeper.managers.RefreshableFragment;

import java.util.ArrayList;

class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.AccountViewHolder>
        implements Filterable {
    private final RefreshableFragment refreshable;
    private final CheckBoxGroup checkBoxGroup = new CheckBoxGroup();
    private Context context;
    private ArrayList<Account> listAccounts;
    private ArrayList<Account> mArrayList;
    private com.example.mybookkeeper.SqliteDatabase mDatabase;

    AccountAdapter(Context context, RefreshableFragment refreshable, ArrayList<Account> listAccounts) {
        this.context = context;
        this.refreshable = refreshable;
        this.listAccounts = listAccounts;
        this.mArrayList = listAccounts;
        mDatabase = new com.example.mybookkeeper.SqliteDatabase(context);
    }

    @Override
    public AccountViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.account_list_layout, parent, false);
        return new AccountViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AccountViewHolder holder, int position) {
        final Account accounts = listAccounts.get(position);
        holder.tvAccName.setText(accounts.getAccountName());
        holder.tvDescription.setText(accounts.getAccDescription());
        checkBoxGroup.addCheckBox(holder.checkBox);
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                checkBoxGroup.activate(holder.checkBox);
                refreshable.navigateToManagers();
            }
        });
        holder.itemView.setOnClickListener(ll -> {
            holder.checkBox.setChecked(!holder.checkBox.isChecked());
        });
        holder.editAccount.setOnClickListener(view -> editTaskDialog(accounts));
        holder.deleteAccount.setOnClickListener(view -> {
            Toast.makeText(context, "Heloo. delete.", Toast.LENGTH_SHORT).show();
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
                    listAccounts = mArrayList;
                } else {
                    ArrayList<Account> filteredList = new ArrayList<>();
                    for (Account accounts : mArrayList) {
                        if (accounts.getAccountName().toLowerCase().contains(charString)) {
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
        View subView = inflater.inflate(R.layout.add_accounts, null);
        final EditText accNameField = subView.findViewById(R.id.enterAccName);
        final EditText descriptionField = subView.findViewById(R.id.enterDescription);
        if (account != null) {
            accNameField.setText(account.getAccountName());
            descriptionField.setText(String.valueOf(account.getAccDescription()));
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Edit account");
        builder.setView(subView);
        builder.create();
        builder.setPositiveButton("EDIT CONTACT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String accountName = accNameField.getText().toString();
                final String description = descriptionField.getText().toString();
                if (TextUtils.isEmpty(accountName) || account == null) {
                    Toast.makeText(context, "Something went wrong. Check your input values", Toast.LENGTH_LONG).show();
                } else {
                    account.setAccountName(accountName);
                    account.setAccDescription(description);
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
        TextView tvAccName, tvDescription;
        ImageView deleteAccount;
        ImageView editAccount;

        AccountViewHolder(View itemView) {
            super(itemView);
            tvAccName = itemView.findViewById(R.id.tvAccName);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            deleteAccount = itemView.findViewById(R.id.deleteAccount);
            editAccount = itemView.findViewById(R.id.editAccount);
            checkBox = itemView.findViewById(R.id.chBoxAccount);
        }
    }
}
