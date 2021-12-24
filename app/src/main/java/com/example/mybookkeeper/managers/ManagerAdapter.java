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
import java.util.Objects;

class ManagerAdapter extends RecyclerView.Adapter<ManagerAdapter.ManagerViewHolder>
        implements Filterable {
    private Context context;
    private final Refreshable refreshable;
    private ArrayList<Manager> listManagers;
    private ArrayList<Manager> mArrayList;
    private SqliteDatabase mDatabase;

    ManagerAdapter(Context context, Refreshable refreshable, ArrayList<Manager> listManagers) {
        this.context = context;
        this.refreshable = refreshable;
        this.listManagers = listManagers;
        this.mArrayList = listManagers;
        mDatabase = new SqliteDatabase(context);
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
        holder.tvJob.setText(manager.getTask());
        holder.editManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTaskDialog(manager);
            }
        });
        holder.deleteManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.deleteManager(manager.getManagerId());
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
                    listManagers = mArrayList;
                } else {
                    ArrayList<Manager> filteredList = new ArrayList<>();
                    for (Manager manager : mArrayList) {
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
        View subView = inflater.inflate(R.layout.add_managers, null);
        final EditText nameField = subView.findViewById(R.id.enterName);
        final EditText managerField = subView.findViewById(R.id.enterTask);
        if (manager != null) {
            nameField.setText(manager.getManagerName());
            managerField.setText(String.valueOf(manager.getTask()));
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Edit manager");
        builder.setView(subView);
        builder.create();
        builder.setPositiveButton("EDIT MANAGER", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String managerName = nameField.getText().toString();
                final String task = managerField.getText().toString();
                if (TextUtils.isEmpty(managerName)) {
                    Toast.makeText(context, "Something went wrong. Check your input values", Toast.LENGTH_LONG).show();
                } else {
                    mDatabase.updateManagers(new Manager(Objects.requireNonNull(manager).getManagerId(), managerName, task));
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

    class ManagerViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvJob;
        ImageView deleteManager;
        ImageView editManager;

        ManagerViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvJob = itemView.findViewById(R.id.tvJob);
            deleteManager = itemView.findViewById(R.id.deleteManager);
            editManager = itemView.findViewById(R.id.editManager);
        }
    }
}
