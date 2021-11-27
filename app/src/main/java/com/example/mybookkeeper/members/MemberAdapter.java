package com.example.mybookkeeper.members;

import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mybookkeeper.databinding.MemberItemBinding;

import java.util.ArrayList;
import java.util.List;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.ViewHolder> {

    int preMemSelectedIndex = -1;
    private final List<Member> mMemValues;
    private MemberList.DialogInterface mMemListener;
    ArrayList Member = new ArrayList<>();

    SparseBooleanArray memCheckBoxStateArray = new SparseBooleanArray();

    public MemberAdapter(List<Member> memitems, MemberList.DialogInterface listener) {
        mMemValues = memitems;
        mMemListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(MemberItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder memholder, int position) {
        memholder.mMemItem = mMemValues.get(position);
        memholder.mTxtMemberName.setText(mMemValues.get(position).getMemberName());
        memholder.mMemCheckBox.setChecked(false);
        memholder.mTxtMemberName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getAdapterPosition returns clicked item position
                Member memmodel = mMemValues.get(position);
                if(!memCheckBoxStateArray.get(position,false))
                {
                    //checkbox checked
                    memholder.mMemCheckBox.setChecked(true);
                    //checkbox state stored.
                    memCheckBoxStateArray.put(position,true);
                    mMemListener.openDialog(memholder.mMemItem);
                }
                else
                {
                    //checkbox unchecked.
                    memholder.mMemCheckBox.setChecked(false);
                    //checkbox state stored
                    memCheckBoxStateArray.put(position,false);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return mMemValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mTxtMemberName;
        public final CheckBox mMemCheckBox;
        public Member mMemItem;

        public ViewHolder(MemberItemBinding binding) {
            super(binding.getRoot());
            mTxtMemberName = binding.txtMemberName;
            mMemCheckBox = binding.memcb;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTxtMemberName.getText() + "'";
        }
    }
}