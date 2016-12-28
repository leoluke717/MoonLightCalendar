package com.example.administrator.moonlightcalendar.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.moonlightcalendar.R;
import com.example.administrator.moonlightcalendar.model.Finance;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 日历适配器
 */
public class CalendarAdapter extends RecyclerView.Adapter {

    public static final int TYPE_SECTION = 1;
    public static final int TYPE_CELL = 2;

    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<List<Finance>> mFinancesList;

    public CalendarAdapter(Context context, List<List<Finance>> financesList) {
        super();
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mFinancesList = financesList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_CELL) {
            return new CalendarViewHolder(mLayoutInflater.inflate(R.layout.month_item, parent, false));
        } else {
            return new MonthViewHolder(mLayoutInflater.inflate(R.layout.month_title_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getClass().equals(CalendarViewHolder.class)) {
            ((CalendarViewHolder) holder).mFinances = mFinancesList.get(position / 2);
        }
        if (holder.getClass().equals(MonthViewHolder.class)) {
            Date date = null;
            List<Finance> finances = mFinancesList.get(position / 2);
            for (Finance finance : finances) {
                if (!finance.isReadOnly()) {
                    date = finance.getDate();
                    break;
                }
            }
            ((MonthViewHolder) holder).setDate(date);
        }
    }

    @Override
    public int getItemCount() {
        if (mFinancesList == null) {
            return 0;
        } else {
            return mFinancesList.size() * 2;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position%2 == 0) {
            return TYPE_SECTION;
        }
        else {
            return TYPE_CELL;
        }
    }

    public class CalendarViewHolder extends RecyclerView.ViewHolder {

        public List<Finance> mFinances;

        public CalendarViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class MonthViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.date_text)
        TextView dateText;

        private Date date;

        public MonthViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setDate(Date date) {
            this.date = date;
            DateFormat dateFormat = new SimpleDateFormat("yyyy年MM月");
            String dateStr = dateFormat.format(date);
            dateText.setText(dateStr);
        }
    }

}