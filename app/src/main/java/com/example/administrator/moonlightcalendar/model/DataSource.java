package com.example.administrator.moonlightcalendar.model;

import android.database.sqlite.SQLiteTransactionListener;
import android.graphics.Color;
import android.util.Log;

import com.example.administrator.moonlightcalendar.Util.myUtil.DateUtil;
import com.example.administrator.moonlightcalendar.Util.myUtil.MoonLightDBUtil;
import com.example.administrator.moonlightcalendar.adapter.CalendarAdapter;

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
    public List<List<Finance>> financesList = new ArrayList<>();
    public CalendarAdapter mAdapter = null;

    private DataSource() {

    }

    public static void init() {
        if (mDataSource == null) {
            mDataSource = new DataSource();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    MoonLightDBUtil.setTransactionListener(mDataSource);
                    mDataSource.initFinance();
                }
            }).start();
        }
    }

    public static DataSource getInstance() {
        init();
        return mDataSource;
    }

    /**
     * 根据月份创建空的每日账单
     */
    private void initFinance() {
        Date date = Person.getInstance().getFirstDate();
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(System.currentTimeMillis());
        //获取初次使用的和现在的月份差距
        int month = calendar2.get(Calendar.MONTH) + calendar2.get(Calendar.YEAR) * 12 -
                calendar1.get(Calendar.MONTH) - calendar1.get(Calendar.YEAR) * 12;
        //创建月份差+12个月的日历
        for (int i = 0; i < 12 + month; i++) {
            List<Finance> list = new ArrayList<>();
            //从初始日期开始，根据月份天数创建账单
            int max = DateUtil.getMonthDaysCount(calendar1.getTime());
            for (int j = 1; j <= max; j++) {
                Finance finance = new Finance(DateUtil.date2String(calendar1.getTime()));
                calendar1.set(Calendar.DAY_OF_MONTH, j);
                finance.setReadOnly(false);
                finance.setBillsMoney(0);
                finance.setDate(DateUtil.date2String(calendar1.getTime()));
                list.add(finance);
            }
            //填充月初月末不满一周的账单
            fillWeekFinance(list);
            financesList.add(list);
            DateUtil.monthAfter(calendar1);
        }
        refreshFinance();
    }

    /**
     * 填充月初和月末不满一周的日期账单
     */
    private void fillWeekFinance(List<Finance> finances) {
        Calendar firstCalendar = Calendar.getInstance();
        firstCalendar.setTime(DateUtil.string2Date(finances.get(0).getDate()));
        int weekday = firstCalendar.get(Calendar.DAY_OF_WEEK);
        for (int i = 0; i < weekday - 1; i++) {
            firstCalendar.add(Calendar.DAY_OF_WEEK, -1);
            Finance finance = new Finance(DateUtil.date2String(firstCalendar.getTime()));
            finance.readOnly = true;
            finances.add(0, finance);
        }

        Calendar lastCalendar = Calendar.getInstance();
        lastCalendar.setTime(DateUtil.string2Date(finances.get(finances.size() - 1).getDate()));
        int lastweekday = lastCalendar.get(Calendar.DAY_OF_WEEK);
        for (int i = 0; i < 7 - lastweekday; i++) {
            lastCalendar.add(Calendar.DAY_OF_WEEK, 1);
            Finance finance = new Finance(DateUtil.date2String(lastCalendar.getTime()));
            finance.readOnly = true;
            finances.add(finance);
        }
    }

    /**
     * 临时计算账单的总金额
     */
    public List<List<Finance>> refreshFinance() {
        //筛选初始日期之后的数据
        long time = Person.getInstance().getFirstDate().getTime();
        List<Bill> bills = MoonLightDBUtil.queryBills("time>?", new String[]{String.valueOf(time)}, null);
        List<Person.CycleProject> cycleProjects = MoonLightDBUtil.queryCycleProject(null, null);
        float originWealth = Person.getInstance().getOriginWealth();
        int i = 0;
        for (List<Finance> finances : financesList) {
            for (Finance finance : finances) {
                //判断账单不是填充的或账单日期小于起始使用日期并且不是同一天
                if (finance.readOnly ||
                        (DateUtil.string2Date(finance.date).getTime() < time &&
                                !finance.date.equals(DateUtil.date2String(new java.sql.Date(time))))) {
                    continue;
                }
                finance.setBillsMoney(0);
                originWealth = originWealth - Person.getInstance().getPayEachDay();
                for (; i < bills.size(); i++) {
                    Bill bill = bills.get(i);
                    if (bill.date.equals(finance.date)) {
                        originWealth = bill.out ? originWealth - bill.price : originWealth + bill.price;
                        finance.setBillsMoney(bill.out ? finance.getBillsMoney() - bill.price : finance.getBillsMoney() + bill.price);
                    } else {
                        break;
                    }
                }
                for (Person.CycleProject cycleProject : cycleProjects) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(DateUtil.string2Date(finance.getDate()));
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    if (day == cycleProject.getDay()) {
                        originWealth = cycleProject.isOut() ?
                                originWealth - cycleProject.getPrice() :
                                originWealth + cycleProject.getPrice();
                        finance.setBillsMoney(cycleProject.isOut() ?
                                finance.getBillsMoney() - cycleProject.getPrice() :
                                finance.getBillsMoney() + cycleProject.getPrice());
                    }
                }
                finance.totalMoney = originWealth;
                finance.financeColor = finance.getColor(finance.totalMoney);
                finance.billsColor = finance.getColor(finance.billsMoney < 0 ? -1 : 3000);
            }
        }
        return financesList;
    }

    /**
     * 数据库事务监听
     */
    @Override
    public void onBegin() {
        Log.d(TAG, "onBegin: ");
    }

    @Override
    public void onCommit() {
        Log.d(TAG, "onCommit: ");
    }

    @Override
    public void onRollback() {
        Log.d(TAG, "onRollback: ");
    }
}
