package com.example.mybookkeeper.transactions;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.mybookkeeper.fragmernts.Expense;
import com.example.mybookkeeper.fragmernts.Receipt;
import com.example.mybookkeeper.fragmernts.Summary;

public class MyAdapter extends FragmentPagerAdapter {
    Context context;
    int totalTabs;
    public MyAdapter(Context c, FragmentManager fm, int totalTabs) {
        super(fm);
        context = c;
        this.totalTabs = totalTabs;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Receipt receipt = new Receipt();
                return receipt;
            case 1:
                Expense expense = new Expense();
                return expense;
            case 2:
                Summary summary = new Summary();
                return summary;
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return totalTabs;
    }
}


