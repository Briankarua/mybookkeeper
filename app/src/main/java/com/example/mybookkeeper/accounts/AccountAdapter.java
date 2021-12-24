package com.example.mybookkeeper.accounts;

import android.app.Activity;
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

import java.util.ArrayList;

class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.AccountViewHolder>
        implements Filterable {
 private Context context;
 private ArrayList<Account> listAccounts;
 private ArrayList<Account> mArrayList;
 private com.example.mybookkeeper.SqliteDatabase mDatabase;
 AccountAdapter(Context context, ArrayList<Account> listAccounts) {
        this.context = context;
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
        holder.editAccount.setOnClickListener(new View.OnClickListener() {
            @Override
    public void onClick(View view) {
                editTaskDialog(accounts);
            }
        });
        holder.deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
    public void onClick(View view) {
                mDatabase.deleteAccount(accounts.getAccountId());
                ((Activity) context).finish();
       context.startActivity(((Activity) context).getIntent());
            }
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
                }
       else {
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
    private void editTaskDialog(final Account accounts) {
    LayoutInflater inflater = LayoutInflater.from(context);
    View subView = inflater.inflate(R.layout.add_accounts, null);
    final EditText accNameField = subView.findViewById(R.id.enterAccName);
    final EditText descriptionField = subView.findViewById(R.id.enterDescription);
    if (accounts != null) {
        accNameField.setText(accounts.getAccountName());
        descriptionField.setText(String.valueOf(accounts.getAccDescription()));
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
                if (TextUtils.isEmpty(accountName)) {
       Toast.makeText(context, "Something went wrong. Check your input values", Toast.LENGTH_LONG).show();
                } else {
       mDatabase.updateAccounts(new Account(accountName, description));
                    ((Activity) context).finish();
       context.startActivity(((Activity)
                            context).getIntent());
                }
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
    public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "Task cancelled",Toast.LENGTH_LONG).show();
            }
        });
        builder.show();
    }
    class AccountViewHolder extends RecyclerView.ViewHolder {
     TextView tvAccName, tvDescription;
     ImageView deleteAccount;
     ImageView editAccount;
     AccountViewHolder(View itemView) {
                super(itemView);
                tvAccName = itemView.findViewById(R.id.tvAccName);
                tvDescription = itemView.findViewById(R.id.tvDescription);
                deleteAccount = itemView.findViewById(R.id.deleteAccount);
                editAccount = itemView.findViewById(R.id.editAccount);
            }
        }
    }
