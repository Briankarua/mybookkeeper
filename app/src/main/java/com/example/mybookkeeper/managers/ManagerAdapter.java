package com.example.mybookkeeper.managers;

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
import com.example.mybookkeeper.SqliteDatabase;

import java.util.ArrayList;
import java.util.Objects;

class ManagerAdapter extends RecyclerView.Adapter<ManagerAdapter.ManagerViewHolder>
        implements Filterable {
 private Context context;
 private ArrayList<Managers> listManagers;
 private ArrayList<Managers> mArrayList;
 private SqliteDatabase mDatabase;
 ManagerAdapter(Context context, ArrayList<Managers> listManagers) {
        this.context = context;
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
        final Managers managers = listManagers.get(position);
        holder.tvName.setText(managers.getManagerName());
        holder.tvJob.setText(managers.getTask());
        holder.editManager.setOnClickListener(new View.OnClickListener() {
            @Override
        public void onClick(View view) {
                    editTaskDialog(managers);
                }
            });
        holder.deleteManager.setOnClickListener(new View.OnClickListener() {
            @Override
    public void onClick(View view) {
                mDatabase.deleteManager(managers.getManagerId());
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
                    listManagers = mArrayList;
                }
       else {
                    ArrayList<Managers> filteredList = new ArrayList<>();
                    for (Managers managers : mArrayList) {
          if (managers.getManagerName().toLowerCase().contains(charString)) {
                            filteredList.add(managers);
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
       listManagers = (ArrayList<Managers>) filterResults.values;
       notifyDataSetChanged();
            }
        };
    }
    @Override
    public int getItemCount() {
    return listManagers.size();
    }
    private void editTaskDialog(final Managers managers) {
    LayoutInflater inflater = LayoutInflater.from(context);
    View subView = inflater.inflate(R.layout.add_managers, null);
    final EditText nameField = subView.findViewById(R.id.enterManager);
    final EditText managerField = subView.findViewById(R.id.enterTask);
    if (managers != null) {
            nameField.setText(managers.getManagerName());
            managerField.setText(String.valueOf(managers.getTask()));
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
       mDatabase.updateManagers(new Managers(Objects.requireNonNull(managers).getManagerId(), managerName, task));
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
