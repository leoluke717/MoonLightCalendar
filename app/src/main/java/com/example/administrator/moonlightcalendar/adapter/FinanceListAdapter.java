package com.example.administrator.moonlightcalendar.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.moonlightcalendar.R;
import com.example.administrator.moonlightcalendar.model.Bill;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

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
            StringBuilder builder = new StringBuilder(key);
            float total = 0;
            billsList.add(builder);
            for (Bill bill : bills) {
                billsList.add(bill);
                total += bill.price;
            }
            BigDecimal b = new BigDecimal(total);
            total = b.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
            builder.append("总价：" + total);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_SECTION) {
            return new TitleViewHolder(mLayoutInflater.inflate(R.layout.month_title_item, parent, false));
        } else {
            return new ContentViewHolder(mLayoutInflater.inflate(R.layout.content_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TitleViewHolder) {
            ((TitleViewHolder) holder).setTitle(billsList.get(position).toString());
        } else {
            ((ContentViewHolder) holder).setBill((Bill) billsList.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return (billsList.get(position) instanceof StringBuilder) ? TYPE_SECTION : TYPE_CELL;
    }

    @Override
    public int getItemCount() {
        return billsList.size();
    }

    /**
     * 头视图的viewHolder
     */
    public class TitleViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.date_text)
        TextView dateText;

        public TitleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setTitle(String title) {
            dateText.setText(title);
        }
    }

    /**
     * 头视图的viewHolder
     */
    public class ContentViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_name)
        TextView mTextName;
        @BindView(R.id.text_price)
        TextView mTextPrice;

        public ContentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setBill(Bill bill) {
            mTextName.setText(bill.from);
            StringBuilder builder = new StringBuilder();
            builder.append(bill.out ? "-" : "+");
            builder.append(bill.price);
            mTextPrice.setText(builder.toString());
        }
    }
}
