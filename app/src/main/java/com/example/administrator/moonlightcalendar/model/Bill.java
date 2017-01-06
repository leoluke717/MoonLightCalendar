package com.example.administrator.moonlightcalendar.model;

import com.example.administrator.moonlightcalendar.Util.myUtil.MoonLightDBUtil;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2016/12/15 0015.
 */

public class Bill implements Serializable{

    public static final int TYPE_DEBT = 0;//欠款，一般不更改
    public static final int TYPE_CYCLE = 1;//周期性的支出或收入
    public static final int TYPE_SPEND = 2;//生活花费

    /**
     * type = debt
     * frpmApp = "蚂蚁花呗"
     * from = "电脑"
     * */

    /**
     * type = cycle
     * fromApp = "固定收支"
     * from = "房租"
     * */

    /**
     * type = spend
     * fromApp = "今日账单"
     * from = "吃饭"
     * */

    public int id;
    public int pID;//project的ID
    public boolean out;//是否花出
    public int type;
    public float price;
    public String fromApp;//来自哪个软件，可以为空，在debt的时候有效
    public String from;//来自哪个项目，对应Project的name
    public String date;
    public long time;

//    存数据库
    public void save() {
        MoonLightDBUtil.insert(this);
    }
}
