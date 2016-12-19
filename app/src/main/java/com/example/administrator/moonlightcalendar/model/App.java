package com.example.administrator.moonlightcalendar.model;

import com.example.administrator.moonlightcalendar.Util.MoonLightDBUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/12/15 0015.
 */

public class App {
    private int createBillDay;
    private int payBillDay;
    private String name;
    private List<Project> projects = new ArrayList<>();

    public void setCreateBillDay(int createBillDay) {
        this.createBillDay = createBillDay;
    }

    public void setPayBillDay(int payBillDay) {
        this.payBillDay = payBillDay;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public String getName() {
        return name;
    }

    public int getPayBillDay() {
        return payBillDay;
    }

    public int getCreateBillDay() {
        return createBillDay;
    }

    public App() {

    }

    public App(String name, int createBillDay, int payBillDay) {
        this.name = name;
        this.createBillDay = createBillDay;
        this.payBillDay = payBillDay;
    }

    public void deleteProject(Project project) {
        projects.remove(project);
    }

    public void createProject(String name, Date createDate, float price, int times) {
        Project project = new Project();
        project.from = this.name;
        project.createDate = createDate;
        project.price = price;
        project.times = times;
        project.name = name;

        if (createBillDay == 0 || payBillDay == 0) {

        }
        else {
            createRegularBills(project);
        }

        project.save();
        projects.add(project);
    }

    //固定日期还款的账单
    private void createRegularBills(Project project) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(project.createDate);//项目开始的日期
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        if (createBillDay > day) {//如交易日小于出账日，本月出账
            calendar.set(Calendar.DAY_OF_MONTH, payBillDay);
        } else {//如交易日大于出账日，下月出账
            calendar.set(Calendar.DAY_OF_MONTH, payBillDay);
            calendar.add(Calendar.MONTH, 1);
            int month = calendar.get(Calendar.MONTH);
            if (month == 1) {//判断是否跨年
                calendar.add(Calendar.YEAR, 1);
            }
        }
        if (payBillDay < createBillDay) {//如还款日小于出账日，则推一个月 如25出 5还 11月26买的物品 1月5日还
            calendar.add(Calendar.MONTH, 1);
            int month = calendar.get(Calendar.MONTH);
            if (month == 1) {//判断是否跨年
                calendar.add(Calendar.YEAR, 1);
            }
        }

        for (int i = 0; i < project.times; i++) {
            Bill bill = new Bill();
            bill.from = project.name;
            bill.fromApp = this.name;
            bill.price = project.price / project.times;
            bill.date = calendar.getTime();
            bill.type = Bill.TYPE_DEBT;
            bill.out = true;
            bill.save();
            calendar.add(Calendar.DATE, 1);
            project.bills.add(bill);
        }
    }

    public void save() {
        MoonLightDBUtil.insert(this);
    }

    /**
     * Created by Administrator on 2016/12/15 0015.
     */

    public static class Project {
        private float price;//总价
        private int times;//次数
        private String from;//来自什么软件,对应App的name
        private String name;//项目名
        private Date createDate;//起始时间
        private List<Bill> bills = new ArrayList<>();

        public void setPrice(float price) {
            this.price = price;
        }

        public void setTimes(int times) {
            this.times = times;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setCreateDate(Date createDate) {
            this.createDate = createDate;
        }

        public void setBills(List<Bill> bills) {
            this.bills = bills;
        }


        public String getFrom() {
            return from;
        }

        public String getName() {
            return name;
        }

        public Date getCreateDate() {
            return createDate;
        }

        public List<Bill> getBills() {
            return bills;
        }

        public float getPrice() {
            return price;
        }

        public int getTimes() {
            return times;
        }

        public Project() {
            bills = new ArrayList<>();
        }

        public void save() {
            MoonLightDBUtil.insert(this);
        }

    }
}
