package com.example.mybookkeeper.managers;


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

import java.util.ArrayList;

class ManagerAdapter extends RecyclerView.Adapter<ManagerAdapter.ManagerViewHolder>
        implements Filterable {
    private final RefreshableFragment refreshable;
    private Context context;
    private ArrayList<Manager> listManagers;
    private SqliteDatabase mDatabase;
    EditText ePhone;

    ManagerAdapter(Context context, RefreshableFragment refreshable, ArrayList<Manager> listManagers, String chooser) {
        this.context = context;
        this.refreshable = refreshable;
        this.listManagers = listManagers;
        mDatabase = new SqliteDatabase(context);
        setHasStableIds(true);
    }

    @Override
    public ManagerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.manager_list_layout, parent, false);
        return new ManagerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ManagerViewHolder holder, int position) {

        final Manager manager = listManagers.get(position);
        holder.tvName.setText(manager.getManagerName());
        holder.tvPhone.setText(manager.getManagerPhone());
        holder.tvPassword.setText(manager.getManagerPassword());

        holder.itemView.setOnClickListener(ll ->{
            refreshable.navigateToManagers(manager);
        });
        holder.editManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTaskDialog(manager);
            }
        });
        holder.deleteManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.deleteManager(manager.getManagerID());
                refreshable.refresh();
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
                    listManagers = listManagers;
                } else {
                    ArrayList<Manager> filteredList = new ArrayList<>();
                    for (Manager manager : listManagers) {
                        if (manager.getManagerName().toLowerCase().contains(charString)) {
                            filteredList.add(manager);
                        }
                    }
                    listManagers = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = listManagers;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listManagers = (ArrayList<Manager>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public int getItemCount() {
        return listManagers.size();
    }

    private void editTaskDialog(final Manager manager) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View accView = inflater.inflate(R.layout.add_managers, null);
        final EditText nameField = accView.findViewById(R.id.enterName);
        final EditText phoneField = accView.findViewById(R.id.enterPhone);
        final EditText passwordField = accView.findViewById(R.id.enterPword);

        if (manager != null) {
            nameField.setText(manager.getManagerName());
            phoneField.setText(manager.getManagerPhone());
            passwordField.setText(manager.getManagerPassword());
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Edit manager");
        builder.setView(accView);
        builder.create();
        builder.setPositiveButton("EDIT CONTACT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String managerName = nameField.getText().toString();
                final String managerPhone = phoneField.getText().toString();
                final String managerPassword = passwordField.getText().toString();
                if (TextUtils.isEmpty(managerPhone) || managerPassword == null) {
                    Toast.makeText(context, "Something went wrong. Check your input values", Toast.LENGTH_LONG).show();
                } else {
                    manager.setManagerName(managerName);
                    manager.setManagerPhone(managerPhone);
                    manager.setManagerPassword(managerPassword);
                    mDatabase.updateManagers(manager);
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

    static class ManagerViewHolder extends RecyclerView.ViewHolder {
        //CheckBox checkBox;
        TextView tvName, tvPhone, tvPassword;
        ImageView deleteManager;
        ImageView editManager;

        ManagerViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            tvPassword = itemView.findViewById(R.id.tvPassword);
            deleteManager = itemView.findViewById(R.id.deleteManager);
            editManager = itemView.findViewById(R.id.editManager);
            //checkBox = itemView.findViewById(R.id.chBoxManager);
        }
    }
}