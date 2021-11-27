package com.example.mybookkeeper.subaccounts;

import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mybookkeeper.databinding.SubaccountItemBinding;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SubAccountAdapter extends RecyclerView.Adapter<SubAccountAdapter.ViewHolder> {

    private final GGroup group;
    int preSubSelectedIndex = -1;
    private final List<SubAccount> mSubValues;
    private SubAccountList.DialogInterface mSubListener;
    ArrayList SubAccount = new ArrayList<>();

    SparseBooleanArray subCheckBoxStateArray = new SparseBooleanArray();

    public SubAccountAdapter(List<SubAccount> subitems, SubAccountList.DialogInterface listener) {
        mSubValues = subitems;
        group = new GGroup();
        mSubListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(SubaccountItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder subholder, int position) {
        group.addButton(subholder.mSubCheckBox );
        subholder.mSubItem = mSubValues.get(position);
        subholder.mTxtSubAcccountName.setText(mSubValues.get(position).getSubAccountName());
        subholder.mSubCheckBox.setChecked(false);
        subholder.mTxtSubAcccountName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle args = new Bundle();
                args.putString("SubAccountName", String.valueOf(position));

                SubAccount submodel = mSubValues.get(position);
                if(!subCheckBoxStateArray.get(position,false))
                {
                    //checkbox checked
                    subholder.mSubCheckBox.setChecked(true);
                    //checkbox state stored.
                    subCheckBoxStateArray.put(position,true);
                    mSubListener.openDialog(subholder.mSubItem);
                }
                else
                {
                    //checkbox unchecked.
                    subholder.mSubCheckBox.setChecked(false);
                    //checkbox state stored
                    subCheckBoxStateArray.put(position,false);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return mSubValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mTxtSubAcccountName;
        public final CheckBox mSubCheckBox;
        public SubAccount mSubItem;

        public ViewHolder(SubaccountItemBinding binding) {
            super(binding.getRoot());
            mTxtSubAcccountName = binding.txtSubAccountName;
            mSubCheckBox = binding.subcb;
            //[KIHARA, Sunday 19th Sep] Added this to uncheck other checkboxes when checkbox
            // is checked
            mSubCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    group.clearCheckExcept(buttonView);
                }
            });
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTxtSubAcccountName.getText() + "'";
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



