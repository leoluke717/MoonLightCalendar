package com.example.administrator.moonlightcalendar.adapter;

import android.content.Context;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.moonlightcalendar.R;
import com.example.administrator.moonlightcalendar.model.Finance;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    private List<Date> mDates = new ArrayList<>();
    private List<MonthAdapter> monthAdapters = new ArrayList<>();

    public CalendarAdapter(Context context, List<List<Finance>> financesList) {
        super();
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mFinancesList = financesList;
        for (int p = 0; p < financesList.size(); p++) {
            List<Finance> finances = mFinancesList.get(p);
            for (Finance finance : finances) {
                if (!finance.isReadOnly()) {
                    mDates.add(finance.getDate());
                    break;
                }
            }
            MonthAdapter monthAdapter = new MonthAdapter(mContext, finances);
            monthAdapters.add(monthAdapter);
        }
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
        if (holder instanceof CalendarViewHolder) {
            ((CalendarViewHolder) holder).setFinances(mFinancesList.get(position / 2));
        }
        if (holder instanceof MonthViewHolder) {

            ((MonthViewHolder) holder).setDate(mDates.get(position / 2));
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
        if (position % 2 == 0) {
            return TYPE_SECTION;
        } else {
            return TYPE_CELL;
        }
    }

    /**
     * 网格视图的viewHolder
     */
    public class CalendarViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.month_recycler)
        RecyclerView monthRecycler;

        public CalendarViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            monthRecycler.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.HORIZONTAL));
            monthRecycler.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        }


        public void setFinances(List<Finance> finances) {
            if (monthRecycler.getLayoutManager() == null) {
                monthRecycler.setLayoutManager(new GridLayoutManager(mContext, 7));
            }
            MonthAdapter adapter = (MonthAdapter) monthRecycler.getAdapter();
            if (adapter == null) {
                monthRecycler.setAdapter(new MonthAdapter(mContext, finances));
            }
            else {
                adapter.setFinances(finances);
                adapter.notifyDataSetChanged();
            }
        }
    }

    /**
     * 头视图的viewHolder
     */
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