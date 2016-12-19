package com.example.administrator.moonlightcalendar.model;

import com.example.administrator.moonlightcalendar.Util.MoonLightDBUtil;

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
    float totalMoney;
    Date date;
    Map<String, List<Bill>> billsMap = new HashMap();
    boolean readOnly;

    public Finance(Date date) {
        this.date = date;
    }

    //这里创建的都是临时不存数据库的
    private void fillList(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.DATE, 1);
        boolean isAfter = date.after(java.sql.Date.valueOf(calendar.get(Calendar.YEAR)+"-"+calendar.get(Calendar.MONTH)+"-"+calendar.get(Calendar.DAY_OF_MONTH)));
        if (isAfter) {
            List<Bill> bills = new ArrayList<>();
            bills.add(createDayPay());
            billsMap.put("预计每日消费", bills);
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
}
