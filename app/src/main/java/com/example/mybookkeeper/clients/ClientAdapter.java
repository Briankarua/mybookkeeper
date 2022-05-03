package com.example.mybookkeeper.clients;

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

import com.example.mybookkeeper.uiutils.CustomNavigation;
import com.example.mybookkeeper.uiutils.RefreshableFragment;
import com.example.mybookkeeper.R;

import java.util.ArrayList;

public class ClientAdapter extends RecyclerView.Adapter<ClientAdapter.ClientViewHolder>
        implements Filterable {
    private final RefreshableFragment refreshable;
    //private final CheckBoxGroup checkBoxGroup = new CheckBoxGroup();
    private Context context;
    private CustomNavigation customNavigation;
    private ArrayList<Client> listClients;
    private ArrayList<Client> mArrayList;
    private com.example.mybookkeeper.SqliteDatabase mDatabase;

    ClientAdapter(Context context,
                  RefreshableFragment refreshable,
                  CustomNavigation customNavigation,
                  ArrayList<Client> listClients) {
        this.context = context;
        this.refreshable = refreshable;
        this.customNavigation = customNavigation;
        this.listClients = listClients;
        this.mArrayList = listClients;
        mDatabase = new com.example.mybookkeeper.SqliteDatabase(context);
    }

    @Override
    public ClientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.client_list_layout, parent, false);
        return new ClientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ClientViewHolder holder, int position) {
        final Client clients = listClients.get(position);
        holder.tvName.setText(clients.getCltName());
        holder.tvMgid.setText("" + clients.getCltMgid());
        holder.tvAccId.setText("" + clients.getCltAccid());
        holder.tvSubId.setText("" + clients.getCltSubId());
//        checkBoxGroup.addCheckBox(holder.checkBox);
//        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            if (isChecked) {
//                checkBoxGroup.activate(holder.checkBox);
//                refreshable.navigateToClientsDialog(clients);
//            }
//        });
        holder.itemView.setOnClickListener(ll -> {
            customNavigation.navigateToClientsDialog(clients);
        });
        holder.editClient.setOnClickListener(view -> editTaskDialog(clients));
        holder.deleteClient.setOnClickListener(view -> {
            Toast.makeText(context, "Heloo. delete.", Toast.LENGTH_SHORT).show();
            mDatabase.deleteClient(clients.getId());
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
                    listClients = mArrayList;
                } else {
                    ArrayList<Client> filteredList = new ArrayList<>();
                    for (Client clients : mArrayList) {
                        if (clients.getCltName().toLowerCase().contains(charString)) {
                            filteredList.add(clients);
                        }
                    }
                    listClients = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = listClients;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listClients = (ArrayList<Client>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public int getItemCount() {
        return listClients.size();
    }

    private void editTaskDialog(final Client client) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View subView = inflater.inflate(R.layout.add_clients, null);
        final EditText clnameField = subView.findViewById(R.id.entercltName);
        final EditText clmgidField = subView.findViewById(R.id.entercltMgid);
        final EditText claccField = subView.findViewById(R.id.entercltaccId);
        final EditText clsubField = subView.findViewById(R.id.entercltSubId);
        if (client != null) {
            clnameField.setText(client.getCltName());
            clmgidField.setText("" +client.getCltMgid());
            claccField.setText("" + client.getCltAccid());
            clsubField.setText("" + client.getCltSubId());
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Edit client");
        builder.setView(subView);
        builder.create();
        builder.setPositiveButton("EDIT CONTACT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String cltName = clnameField.getText().toString();
                final int cltMgid = Integer.parseInt(clmgidField.getText().toString());
                final int cltaccId = Integer.parseInt(claccField.getText().toString());
                final int cltsubId = Integer.parseInt(clsubField.getText().toString());
                if (TextUtils.isEmpty(cltName) || client == null) {
                    Toast.makeText(context, "Something went wrong. Check your input values", Toast.LENGTH_LONG).show();
                } else {
                    client.setCltName(cltName);
                    client.setCltMgid(cltMgid);
                    client.setCltAccid(cltaccId);
                    client.setCltSubId(cltsubId);
                    mDatabase.updateClients(client);
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

    static class ClientViewHolder extends RecyclerView.ViewHolder {
        // CheckBox checkBox;
        TextView tvName, tvMgid, tvAccId, tvSubId;
        ImageView deleteClient;
        ImageView editClient;

        ClientViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvcltName);
            tvMgid = itemView.findViewById(R.id.tvcltMgid);
            tvAccId = itemView.findViewById(R.id.tvcltAccId);
            tvSubId = itemView.findViewById(R.id.tvcltSubId);
            deleteClient = itemView.findViewById(R.id.deleteClient);
            editClient = itemView.findViewById(R.id.editClient);
            //checkBox = itemView.findViewById(R.id.chBoxClient);
        }
    }
}
