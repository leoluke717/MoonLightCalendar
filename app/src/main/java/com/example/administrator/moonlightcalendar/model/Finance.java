package com.example.administrator.moonlightcalendar.model;

import android.graphics.Color;
import android.text.format.DateUtils;
import android.util.SparseArray;

import com.example.administrator.moonlightcalendar.Util.MoonLightDBUtil;
import com.example.administrator.moonlightcalendar.Util.TimeUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/15 0015.
 * 负责做显示的财务类，代表一天的收支情况，包含了
 * Date：当天日期
 * titleList：收支的类型标题（如工资、蚂蚁花呗、生活费）
 * billMap：账单列表，其中
 */

/**
 * 每日
 */
public class Finance {

    float billsMoney;//账单总金额
    float totalMoney;//目前剩余金额
    boolean readOnly;//是否为填充的视图，填充不显示
    boolean isToday;//是否为今天
    int financeColor;//账单等级颜色
    int billsColor;//账单金额颜色，支出为红，收入为绿
    Date date;
    Map<String, List<Bill>> billsMap = new HashMap();//账单表

    public Finance(Date date) {
        this.date = date;
    }

    //这里创建的都是临时不存数据库的
    private void fillList(Date date) {
        isToday = DateUtils.isToday(date.getTime());
        boolean isAfter = date.after(new java.sql.Date(System.currentTimeMillis())) && isToday;
        if (isAfter) {
            List<Bill> bills = new ArrayList<>();
            bills.add(createDayPay());
            billsMap.put("预计每日消费", bills);
        }
    }

    public int getColor(float totalMoney) {
        if (totalMoney < 0) {
            return Color.rgb(255, 0, 0);
        } else if (totalMoney < 100) {
            return Color.rgb(255, 99, 71);
        } else if (totalMoney < 500) {
            return Color.rgb(184, 134, 11);
        } else if (totalMoney < 2000) {
            return Color.rgb(107, 142, 35);
        } else {
            return Color.rgb(34, 139, 34);
        }
    }

    private Bill createDayPay() {
        Bill bill = new Bill();
        bill.type = Bill.TYPE_SPEND;
        bill.price = Person.getInstance().getPayEachDay();
        bill.from = "消费";
        bill.fromApp = "预计每日消费";
        bill.date = date;
        return bill;
    }

    private void getList() {
        for (App app : Person.getInstance().getApps()) {
            List<Bill> bills = MoonLightDBUtil.queryBills("fromapp=?", new String[]{app.getName()});
            billsMap.put(app.getName(), bills);
        }
    }

    public float getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(float totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Map<String, List<Bill>> getBillsMap() {
        return billsMap;
    }

    public void setBillsMap(Map<String, List<Bill>> billsMap) {
        this.billsMap = billsMap;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public float getBillsMoney() {
        return billsMoney;
    }

    public void setBillsMoney(float billsMoney) {
        this.billsMoney = billsMoney;
    }

    public boolean isToday() {
        return isToday;
    }

    public void setToday(boolean today) {
        isToday = today;
    }

    public int getFinanceColor() {
        return financeColor;
    }

    public void setFinanceColor(int financeColor) {
        this.financeColor = financeColor;
    }

    public int getBillsColor() {
        return billsColor;
    }

    public void setBillsColor(int billsColor) {
        this.billsColor = billsColor;
    }
}
