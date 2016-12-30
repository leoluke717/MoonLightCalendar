package com.example.administrator.moonlightcalendar.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.moonlightcalendar.R;
import com.example.administrator.moonlightcalendar.model.Finance;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/12/28 0028.
 */

public class DayTableAdapter extends RecyclerView.Adapter {

    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<Finance> mFinances;

    public DayTableAdapter(Context context, List<Finance> finances) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mFinances = finances;
    }

    public List<Finance> getFinances() {
        return mFinances;
    }

    public void setFinances(List<Finance> finances) {
        mFinances = finances;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DayViewHolder(mLayoutInflater.inflate(R.layout.day_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((DayViewHolder) holder).setFinance(mFinances.get(position));
    }

    @Override
    public int getItemCount() {
        return mFinances.size();
    }

    public class DayViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.day_text)
        TextView dayText;
        @BindView(R.id.bill_text)
        TextView mBillText;

        private Finance mFinance;

        public DayViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public Finance getFinance() {
            return mFinance;
        }

        public void setFinance(Finance finance) {
            mFinance = finance;
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(finance.getDate());
            dayText.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
            dayText.setTextColor(finance.getFinanceColor());
            dayText.setVisibility(finance.isReadOnly() ? View.INVISIBLE : View.VISIBLE);
            mBillText.setText(String.valueOf(finance.getBillsMoney()));
            mBillText.setTextColor(ContextCompat.getColor(mContext, R.color.cardview_dark_background));
            mBillText.setVisibility(finance.getBillsMoney() == 0 ? View.INVISIBLE : View.VISIBLE);
        }
    }
}
