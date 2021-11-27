package com.example.mybookkeeper.home;

import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mybookkeeper.R;
import com.example.mybookkeeper.databinding.HomeItemBinding;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private final GGroup group;
    int preSelectedIndex = -1;
    private final List<MyHome> mHomeValues;
    private final BaseKiharaNavigator navigator;
    //private Home.DialogInterface mHomeListener;
    ArrayList Home = new ArrayList<>();

    SparseBooleanArray checkBoxStateArray = new SparseBooleanArray();

    public HomeAdapter(List<MyHome> homeValues, BaseKiharaNavigator navigator) {
        mHomeValues = homeValues;
        this.navigator = navigator;
        group = new GGroup();
    }

/*    public HomeAdapter(List<Home> homeItems, Home.DialogInterface listener) {
        mHomeValues = homeItems;
        group = new GGroup();
        mHomeListener = listener;
    }*/

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(HomeItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        //[KIHARA, Sunday 19 Sep] Added checkbox to group
        group.addButton(holder.mHomeCheckBox);

        holder.mHomeItem = mHomeValues.get(position);
        holder.mTxtHomName.setText(mHomeValues.get(position).getMyHomeName());
        holder.mHomeCheckBox.setChecked(false);
        holder.mImageView.setImageResource(mHomeValues.get(position).getImgId());
        holder.mHomeCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                group.clearCheckExcept(buttonView);
                navigator.switchToFragment(mHomeValues.get(position).getNavigationId());
            }
        });
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View v) {
                //getAdapterPosition returns clicked item position
                //Home model = mHomeValues.get(position);
               if (!checkBoxStateArray.get(position, false)) {
                    //checkbox checked
                    holder.mHomeCheckBox.setChecked(true);
                    //checkbox state stored.
                    checkBoxStateArray.put(position, true);
                    //mHomeListener.openDialog(holder.mHomeItem);
                } else {
                   //checkbox unchecked.
                    holder.mHomeCheckBox.setChecked(false);
                    //checkbox state stored
                    checkBoxStateArray.put(position, false);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mHomeValues.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mTxtHomName;
        public final CheckBox mHomeCheckBox;
        public final ImageView mImageView;
        public MyHome mHomeItem;

        public RelativeLayout relativeLayout;

        public ViewHolder(HomeItemBinding binding) {
            super(binding.getRoot());
            mTxtHomName = binding.txtHomName;
            mHomeCheckBox = binding.homeCb;
            mImageView = binding.imageView;
            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.relativeLayout);

            //[KIHARA, Sunday 19th Sep] Added this to uncheck other checkboxes when checkbox
            // is checked
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTxtHomName.getText() + "'";
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