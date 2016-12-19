package com.example.administrator.moonlightcalendar.model;

import android.database.sqlite.SQLiteTransactionListener;
import android.util.Log;

import com.example.administrator.moonlightcalendar.Util.DateUtil;
import com.example.administrator.moonlightcalendar.Util.MoonLightDBUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/12/15 0015.
 * 用于界面显示的类，其中有每天的财务状况
 */

public class DataSource implements SQLiteTransactionListener {
    
    public static final String TAG = "DataSource";

    private static DataSource mDataSource;
    public List<Finance> finances = new ArrayList<>();

    private DataSource() {

    }

    public static DataSource getInstance() {
        if (mDataSource == null) {
            mDataSource = new DataSource();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    MoonLightDBUtil.setTransactionListener(mDataSource);
                    mDataSource.init();
                }
            }).start();
        }
        return mDataSource;
    }

    private void init() {
        Date date = Person.getInstance().getFirstDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int differ = DateUtil.DifferOfDays(date, new java.sql.Date(System.currentTimeMillis()));
        for (int i = 0; i < 365 + differ; i++) {
            long newTime = date.getTime() + (24 * 60 * 60 * 1000) * i;
            Finance finance = new Finance(new java.sql.Date(newTime));
            finances.add(finance);
        }
        refreshFinance();
    }

//    临时计算总值
    public List<Finance> refreshFinance() {
        List<Bill> bills = MoonLightDBUtil.queryBills(null, null);
        float originWealth = Person.getInstance().getOriginWealth();
        for (Finance finance : finances) {
            originWealth = originWealth - Person.getInstance().getPayEachDay();
            for (Bill bill : bills) {
                if (DateUtil.isTheSameDay(bill.date, finance.date)) {
                    originWealth = bill.out ? originWealth - bill.price : originWealth + bill.price;
                    bills.remove(bill);
                } else {
                    break;
                }
            }
            finance.totalMoney = originWealth;
        }
        return finances;
    }

    /**
     * 数据库事务监听
     * */
    @Override
    public void onBegin() {
        Log.d(TAG, "onBegin: ");
    }

    @Override
    public void onCommit() {
        Log.d(TAG, "onCommit: ");
        refreshFinance();
    }

    @Override
    public void onRollback() {
        Log.d(TAG, "onRollback: ");
    }
}
