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

import java.util.ArrayList;
import java.util.Objects;

class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder>
        implements Filterable {
 private Context context;
 private ArrayList<Contacts> listContacts;
 private ArrayList<Contacts> mArrayList;
 private SqliteDatabase mDatabase;
 ContactAdapter(Context context, ArrayList<Contacts> listContacts) {
        this.context = context;
        this.listContacts = listContacts;
        this.mArrayList = listContacts;
        mDatabase = new SqliteDatabase(context);
    }
    @Override
 public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_list_layout, parent, false);
        return new ContactViewHolder(view);
    }
    @Override
 public void onBindViewHolder(ContactViewHolder holder, int position) {
        final Contacts contacts = listContacts.get(position);
        holder.tvName.setText(contacts.getName());
        holder.tvJob.setText(contacts.getJob());
        holder.editContact.setOnClickListener(new View.OnClickListener() {
            @Override
    public void onClick(View view) {
                editTaskDialog(contacts);
            }
        });
        holder.deleteContact.setOnClickListener(new View.OnClickListener() {
            @Override
    public void onClick(View view) {
                mDatabase.deleteContact(contacts.getId());
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
                    listContacts = mArrayList;
                }
       else {
                    ArrayList<Contacts> filteredList = new ArrayList<>();
                    for (Contacts contacts : mArrayList) {
          if (contacts.getName().toLowerCase().contains(charString)) {
                            filteredList.add(contacts);
                        }
                    }
                    listContacts = filteredList;
                }
       FilterResults filterResults = new FilterResults();
       filterResults.values = listContacts;
       return filterResults;
            }
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
       listContacts = (ArrayList<Contacts>) filterResults.values;
       notifyDataSetChanged();
            }
        };
    }
    @Override
    public int getItemCount() {
    return listContacts.size();
    }
    private void editTaskDialog(final Contacts contacts) {
    LayoutInflater inflater = LayoutInflater.from(context);
    View subView = inflater.inflate(R.layout.add_contacts, null);
    final EditText nameField = subView.findViewById(R.id.enterName);
    final EditText contactField = subView.findViewById(R.id.enterJob);
    if (contacts != null) {
            nameField.setText(contacts.getName());
            contactField.setText(String.valueOf(contacts.getJob()));
        }
    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    builder.setTitle("Edit contact");
    builder.setView(subView);
    builder.create();
    builder.setPositiveButton("EDIT CONTACT", new DialogInterface.OnClickListener() {
            @Override
    public void onClick(DialogInterface dialog, int which) {
                final String name = nameField.getText().toString();
                final String ph_no = contactField.getText().toString();
                if (TextUtils.isEmpty(name)) {
       Toast.makeText(context, "Something went wrong. Check your input values", Toast.LENGTH_LONG).show();
                } else {
       mDatabase.updateContacts(new Contacts(Objects.requireNonNull(contacts).getId(), name, ph_no));
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
    class ContactViewHolder extends RecyclerView.ViewHolder {
     TextView tvName, tvJob;
     ImageView deleteContact;
     ImageView editContact;
     ContactViewHolder(View itemView) {
                super(itemView);
                tvName = itemView.findViewById(R.id.tvName);
                tvJob = itemView.findViewById(R.id.tvJob);
                deleteContact = itemView.findViewById(R.id.deleteContact);
                editContact = itemView.findViewById(R.id.editContact);
            }
        }
    }