package com.example.administrator.moonlightcalendar.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.administrator.moonlightcalendar.model.Bill;
import com.example.administrator.moonlightcalendar.model.Finance;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static android.R.attr.keySet;

/**
 * Created by Administrator on 2017/1/6 0006.
 */

public class FinanceListAdapter extends RecyclerView.Adapter {

    public static final int TYPE_SECTION = 1;
    public static final int TYPE_CELL = 2;

    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private Map<String, List<Bill>> billsMap;
    private List<Object> billsList = new ArrayList<>();

    public FinanceListAdapter(Context context, Map<String, List<Bill>> billsMap) {
        mContext = context;
        this.billsMap = billsMap;
        dealBillsMap();
        mLayoutInflater = LayoutInflater.from(context);
    }

    private void dealBillsMap() {
        Set<String> keySet = billsMap.keySet();
        for (String key : keySet) {
            List<Bill> bills = billsMap.get(key);
            billsList.add(key);
            for (Bill bill : bills) {
                billsList.add(bill);
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
