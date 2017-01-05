package com.example.administrator.moonlightcalendar.adapter;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.moonlightcalendar.R;
import com.example.administrator.moonlightcalendar.Util.myUtil.DateUtil;
import com.example.administrator.moonlightcalendar.model.Finance;
import com.example.administrator.moonlightcalendar.other.MyItemDecoration;

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
public class CalendarAdapter extends RecyclerView.Adapter{

    public interface onItemClickListener {
        abstract void onClick(int section, int position);
    }

    public static final int TYPE_SECTION = 1;
    public static final int TYPE_CELL = 2;

    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<List<Finance>> mFinancesList;
    private List<Date> mDates = new ArrayList<>();
    private List<DayTableAdapter> mDayTableAdapters = new ArrayList<>();
    private onItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(onItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public CalendarAdapter(Context context, List<List<Finance>> financesList) {
        super();
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mFinancesList = financesList;
        for (int p = 0; p < financesList.size(); p++) {
            List<Finance> finances = mFinancesList.get(p);
            for (Finance finance : finances) {
                if (!finance.isReadOnly()) {
                    mDates.add(DateUtil.string2Date(finance.getDate()));
                    break;
                }
            }
            DayTableAdapter dayTableAdapter = new DayTableAdapter(mContext, finances);
            mDayTableAdapters.add(dayTableAdapter);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_CELL) {
            return new DayTableViewHolder(mLayoutInflater.inflate(R.layout.month_item, parent, false));
        } else {
            return new TitleViewHolder(mLayoutInflater.inflate(R.layout.month_title_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DayTableViewHolder) {
            ((DayTableViewHolder) holder).setFinances(mFinancesList.get(position / 2));
            holder.itemView.setTag(position/2);
        }
        if (holder instanceof TitleViewHolder) {

            ((TitleViewHolder) holder).setDate(mDates.get(position / 2));
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
    public class DayTableViewHolder extends RecyclerView.ViewHolder implements DayTableAdapter.DayOnClickListener{

        @BindView(R.id.month_recycler)
        RecyclerView monthRecycler;

        public DayTableViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            monthRecycler.addItemDecoration(new MyItemDecoration(mContext, DividerItemDecoration.HORIZONTAL));
            monthRecycler.addItemDecoration(new MyItemDecoration(mContext, DividerItemDecoration.VERTICAL));
            monthRecycler.setLayoutManager(new GridLayoutManager(mContext, 7));
            monthRecycler.setHasFixedSize(true);
        }


        public void setFinances(List<Finance> finances) {
            DayTableAdapter adapter = (DayTableAdapter) monthRecycler.getAdapter();
            if (adapter == null) {
                adapter = new DayTableAdapter(mContext, finances);
                monthRecycler.setAdapter(adapter);
                monthRecycler.setItemAnimator(new DefaultItemAnimator());
            } else {
                adapter.setFinances(finances);
                adapter.notifyDataSetChanged();
            }
            adapter.setDayOnClickListener(this);
        }

        @Override
        public void onclick(int position) {
            mOnItemClickListener.onClick(((Integer) itemView.getTag()).intValue(), position);
        }
    }

    /**
     * 头视图的viewHolder
     */
    public class TitleViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.date_text)
        TextView dateText;

        private Date date;

        public TitleViewHolder(View itemView) {
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