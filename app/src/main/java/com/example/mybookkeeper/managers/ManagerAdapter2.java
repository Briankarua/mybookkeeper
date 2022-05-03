package com.example.mybookkeeper.managers;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybookkeeper.R;
import com.example.mybookkeeper.uiutils.CustomNavigation;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

import io.getstream.avatarview.AvatarView;

class ManagerAdapter2 extends PagingDataAdapter<Manager, ManagerAdapter2.ManagerViewHolder> {

    public static class ManagerComparator extends DiffUtil.ItemCallback<Manager> {

        @Override
        public boolean areItemsTheSame(@NonNull Manager oldItem, @NonNull Manager newItem) {
            return oldItem.getManagerID() == newItem.getManagerID();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Manager oldItem, @NonNull Manager newItem) {
            return oldItem.equals(newItem);
        }
    }

    private final Context context;
    private final CustomNavigation customNavigation;

    public ManagerAdapter2(@NonNull DiffUtil.ItemCallback<Manager> diffCallback,
                           Context context,
                           CustomNavigation customNavigation) {
        super(diffCallback);
        this.context = context;
        this.customNavigation = customNavigation;
    }

    @NonNull
    @Override
    public ManagerAdapter2.ManagerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.manager_list_layout_2, parent, false);
        return new ManagerAdapter2.ManagerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ManagerAdapter2.ManagerViewHolder holder, int position) {
        final Manager manager = getItem(position);
        if (manager == null) {
            return;
        }
        holder.tvName.setText(manager.getManagerName());
        holder.avatarView.setAvatarInitials(initialsOf(manager.getManagerName()));
        holder.tvPhone.setText(manager.getManagerPhone());
        String idString = String.format(context.getString(R.string.manager_id_str_msg), manager.getManagerID());
        holder.tvManagerId.setText(idString);

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        currencyFormat.setCurrency(Currency.getInstance("KES"));
        String balances = currencyFormat.format(manager.getTotalBalances());
        holder.tvBalances.setText(balances);
        String expenses = currencyFormat.format(manager.getTotalExpenses());
        holder.tvExpenses.setText(expenses);
        String receipts = currencyFormat.format(manager.getTotalReceipts());
        holder.tvReceipts.setText(receipts);

        holder.itemView.setOnClickListener(ll -> customNavigation.navigateToAccounts(manager));
        holder.itemView.setOnLongClickListener(view -> {
            view.setSelected(true);
            return true;
        });
    }

    private String initialsOf(String managerName) {
        final StringBuilder initials = new StringBuilder();
        final String[] s = managerName.split(" ");
        for (String name : s){
            if(name.trim().isEmpty()){
                continue;
            }
            char c = name.trim().charAt(0);
            initials.append(c);
        }
        return initials.toString();
    }

    static class ManagerViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvManagerId;
        private final TextView tvBalances;
        private final TextView tvExpenses;
        private final TextView tvReceipts;
        private final TextView tvName, tvPhone;
        private final AvatarView avatarView;

        ManagerViewHolder(View itemView) {
            super(itemView);
            avatarView = itemView.findViewById(R.id.managerProfileImg);
            tvName = itemView.findViewById(R.id.managerName);
            tvPhone = itemView.findViewById(R.id.managerPhone);
            tvManagerId = itemView.findViewById(R.id.managerIdTxtView);
            tvBalances = itemView.findViewById(R.id.balancesTextView);
            tvExpenses = itemView.findViewById(R.id.expensesTextView);
            tvReceipts = itemView.findViewById(R.id.receiptsTextView);
        }
    }
}