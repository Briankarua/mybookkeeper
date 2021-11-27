package com.example.mybookkeeper.clients;

import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mybookkeeper.databinding.ClientItemBinding;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClientAdapter extends RecyclerView.Adapter<ClientAdapter.ViewHolder> {

    private final GGroup group;
    int preSelectedIndex = -1;
    private final List<Client> mValues;
    private ClientList.DialogInterface mListener;
    ArrayList Client = new ArrayList<>();

    SparseBooleanArray checkBoxStateArray = new SparseBooleanArray();

    public ClientAdapter(List<Client> items, ClientList.DialogInterface listener) {
        mValues = items;
        group = new GGroup();
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(ClientItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        //[KIHARA, Sunday 19 Sep] Added checkbox to group
        group.addButton(holder.mCheckBox);
        holder.mItem = mValues.get(position);
        holder.mTxtClintName.setText(mValues.get(position).getClientName());
        holder.mCheckBox.setChecked(false);
        holder.mTxtClintName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle args = new Bundle();
                args.putString("ClientName", String.valueOf(position));

                //System.out.println("VVZ Clicked " + holder.mCheckBox);
                //getAdapterPosition returns clicked item position
                Client model = mValues.get(position);
                if (!checkBoxStateArray.get(position, false)) {
                    //checkbox checked
                    holder.mCheckBox.setChecked(true);
                    //checkbox state stored.
                    checkBoxStateArray.put(position, true);
                    mListener.openDialog(holder.mItem);
                } else {
                    //checkbox unchecked.
                    holder.mCheckBox.setChecked(false);
                    //checkbox state stored
                    checkBoxStateArray.put(position, false);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mTxtClintName;
        public final CheckBox mCheckBox;
        public Client mItem;

        public ViewHolder(ClientItemBinding binding) {
            super(binding.getRoot());
            mTxtClintName = binding.txtClientName;
            mCheckBox = binding.clientCb;
            //[KIHARA, Sunday 19th Sep] Added this to uncheck other checkboxes when checkbox
            // is checked
            mCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    group.clearCheckExcept(buttonView);
                }
            });
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTxtClintName.getText() + "'";
        }
    }

    public static class GGroup {
        private final Set<CheckBox> checkboxes = new HashSet<>();

        GGroup() {

        }

        public Set<CheckBox> getCheckboxes() {
            return checkboxes;
        }

        public void addButton(CheckBox button) {
            checkboxes.add(button);
        }

        public void clearCheck() {
            for (CheckBox checkBox : checkboxes) {
                checkBox.setChecked(false);
            }
        }

        public void clearCheckExcept(CompoundButton buttonView) {
            for (CheckBox checkBox : checkboxes) {
                if (checkBox.equals(buttonView)) {
                    continue;
                }
                checkBox.setChecked(false);
            }
        }
    }
}
