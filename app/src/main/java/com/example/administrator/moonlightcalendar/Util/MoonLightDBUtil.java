package com.example.administrator.moonlightcalendar.Util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteTransactionListener;

import com.example.administrator.moonlightcalendar.model.App;
import com.example.administrator.moonlightcalendar.model.Bill;
import com.example.administrator.moonlightcalendar.model.Person;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/9 0009.
 */

public class MoonLightDBUtil {
    private static MoonLightDBHelper helper;
    private static SQLiteDatabase db;
    private static SQLiteTransactionListener transactionListener;

    public static void setTransactionListener(SQLiteTransactionListener transactionListener) {
        MoonLightDBUtil.transactionListener = transactionListener;
    }

    public static void init(Context context) {
        helper = new MoonLightDBHelper(context);
        db = helper.getWritableDatabase();
    }

//    public static void insertOrUpdate(RecordBean recordBean) {
//        RecordBean recordBean1 = getDevice(recordBean.getSubDeviceNum());
//        int originCount = recordBean1 == null ? 0 : recordBean1.getCount();
//        if (recordBean.isState()) {
//            if (recordBean1 == null) {
//                return;
//            }
//            else {
//                originCount = 0;
//            }
//        }
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("main_num", mainDeviceNum);
//        contentValues.put("sub_num", recordBean.getSubDeviceNum());
//        contentValues.put("model", recordBean.getModel());
//        contentValues.put("state", recordBean.isState() ? 1 : 0);
//        contentValues.put("count", recordBean.getCount() + originCount);
//        contentValues.put("time", recordBean.getTime());
//        contentValues.put("location", recordBean.getLocation());
//        db.replace("record", null, contentValues);
//    }
//
//    private static RecordBean getDevice(int sub_num) {
//        String where = "sub_num=?";
//        String args[] = {String.valueOf(sub_num)};
//        Cursor cursor = db.query("record", null, where, args, null, null, null);
//        if (cursor.moveToFirst()) {
//            do {
//                RecordBean recordBean = new RecordBean();
//                recordBean.setMainDeviceNum(cursor.getInt(cursor.getColumnIndex("main_num")));
//                recordBean.setSubDeviceNum(cursor.getInt(cursor.getColumnIndex("sub_num")));
//                recordBean.setTime(cursor.getInt(cursor.getColumnIndex("sub_num")));
//                recordBean.setLocation(cursor.getString(cursor.getColumnIndex("location")));
//                recordBean.setModel(cursor.getString(cursor.getColumnIndex("model")));
//                recordBean.setState(cursor.getInt(cursor.getColumnIndex("state")) == 1);
//                recordBean.setCount(cursor.getInt(cursor.getColumnIndex("count")));
//                cursor.close();
//                return recordBean;
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        return null;
//    }
//
//    public static List<RecordBean> query() {
//        String orderBy = "time desc";
//        String where = "main_num=?";
//        List<RecordBean> list = new ArrayList<>();
//        String args[] = {String.valueOf(mainDeviceNum)};
//        if (db == null) {
//            return list;
//        }
//        Cursor cursor = db.query("record", null, where, args, null, null, orderBy);
//        if (cursor.moveToFirst()) {
//            do {
//                RecordBean recordBean = new RecordBean();
//                recordBean.setMainDeviceNum(cursor.getInt(cursor.getColumnIndex("main_num")));
//                recordBean.setSubDeviceNum(cursor.getInt(cursor.getColumnIndex("sub_num")));
//                recordBean.setTime(cursor.getInt(cursor.getColumnIndex("time")));
//                recordBean.setLocation(cursor.getString(cursor.getColumnIndex("location")));
//                recordBean.setModel(cursor.getString(cursor.getColumnIndex("model")));
//                recordBean.setState(cursor.getInt(cursor.getColumnIndex("state")) == 1);
//                recordBean.setCount(cursor.getInt(cursor.getColumnIndex("count")));
//                list.add(recordBean);
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        return list;
//    }
//
//    public static void clear() {
//        db.delete("record", null, null);
//    }

    /**
     * 增
     */
    public static void insert(App app) {
        List<App> list = queryApp("name=?", new String[]{app.getName()});
        if (!list.isEmpty()) {
            update(app);
            return;
        }
        db.beginTransactionWithListener(transactionListener);
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", app.getName());
            contentValues.put("paybillday", app.getPayBillDay());
            contentValues.put("createbillday", app.getCreateBillDay());
            db.insert("app", null, contentValues);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public static void insert(App.Project project) {
        List<App.Project> list = queryProject("name=?", new String[]{project.getName()});
        if (!list.isEmpty()) {
            update(project);
            return;
        }
        db.beginTransactionWithListener(transactionListener);
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", project.getName());
            contentValues.put("_from", project.getFrom());
            contentValues.put("price", project.getPrice());
            contentValues.put("times", project.getTimes());
            contentValues.put("createdate", project.getCreateDate().getTime());
            db.insert("project", null, contentValues);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public static void insert(Bill bill) {
        if (!queryBills("_from=? and fromapp=?", new String[]{bill.from, bill.fromApp}).isEmpty()) {
            update(bill);
            return;
        }
        db.beginTransactionWithListener(transactionListener);
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("_from", bill.from);
            contentValues.put("fromapp", bill.fromApp);
            contentValues.put("price", bill.price);
            contentValues.put("date", bill.date.getTime());
            contentValues.put("out", bill.out ? 1 : 0);
            contentValues.put("type", bill.type);
            db.insert("bill", null, contentValues);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public static void insert(Person.CycleProject project) {
        List<Person.CycleProject> list = queryCycleProject("name=?", new String[]{project.getName()});
        if (!list.isEmpty()) {
            update(project);
            return;
        }
        db.beginTransactionWithListener(transactionListener);
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", project.getName());
            contentValues.put("price", project.getPrice());
            contentValues.put("out", project.isOut() ? 1 : 0);
            contentValues.put("day", project.getDay());
            db.insert("cycle_project", null, contentValues);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    /**
     * 删
     */
    public static void deleteApp(App app) {
        String where = "name=?";
        String[] args = new String[]{app.getName()};
        db.delete("app", where, args);
        db.delete("project", "_from=?", new String[]{app.getName()});
        db.delete("bill", "fromapp=?", new String[]{app.getName()});
    }

    public static void deleteProject(App.Project project) {
        String where = "name=?";
        String[] args = new String[]{project.getName()};
        db.delete("project", where, args);
        db.delete("bill", "_from=? and fromapp", new String[]{project.getName(), project.getFrom()});

    }

    public static void deleteCycleProject(Person.CycleProject project) {
        String where = "name=?";
        String[] args = new String[]{project.getName()};
        db.delete("cycle_project", where, args);
    }

    public static void deleteBills(Bill bill) {
        String where = "_from=? and fromapp=?";
        String[] args = new String[]{bill.from, bill.fromApp};
        db.delete("bill", where, args);
    }

    public static void clear() {
        db.delete("app", null, null);
        db.delete("project", null, null);
        db.delete("cycle_project", null, null);
        db.delete("bill", null, null);
    }

    /**
     * 改
     */
    public static void update(App app) {
        String where = "name=?";
        String[] args = new String[]{app.getName()};
        db.beginTransactionWithListener(transactionListener);
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", app.getName());
            contentValues.put("paybillday", app.getPayBillDay());
            contentValues.put("createbillday", app.getCreateBillDay());
            db.update("app", contentValues, where, args);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public static void update(App.Project project) {
        String where = "name=?";
        String[] args = new String[]{project.getName()};
        db.beginTransactionWithListener(transactionListener);
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", project.getName());
            contentValues.put("_from", project.getFrom());
            contentValues.put("price", project.getPrice());
            contentValues.put("times", project.getTimes());
            contentValues.put("createdate", project.getCreateDate().getTime());
            db.update("project", contentValues, where, args);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public static void update(Person.CycleProject project) {
        String where = "name=?";
        String[] args = new String[]{project.getName()};
        db.beginTransactionWithListener(transactionListener);
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", project.getName());
            contentValues.put("price", project.getPrice());
            contentValues.put("out", project.isOut() ? 1 : 0);
            contentValues.put("day", project.getDay());
            db.update("cycle_project", contentValues, where, args);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public static void update(Bill bill) {
        String where = "_from=? and fromapp=?";
        String[] args = new String[]{bill.from, bill.fromApp};
        db.beginTransactionWithListener(transactionListener);
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("_from", bill.from);
            contentValues.put("fromapp", bill.fromApp);
            contentValues.put("price", bill.price);
            contentValues.put("date", bill.date.getTime());
            contentValues.put("out", bill.out ? 1 : 0);
            contentValues.put("type", bill.type);
            db.update("bill", contentValues, where, args);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    /**
     * 查
     */
    public static List<Bill> queryBills(String where, String[] args) {
        String orderBy = "date asc";
        List<Bill> bills = new ArrayList<>();
        if (db == null) {
            return bills;
        }
        Cursor cursor = db.query("bill", null, where, args, null, null, orderBy);
        if (cursor.moveToFirst()) {
            do {
                Bill bill = new Bill();
                bill.from = cursor.getString(cursor.getColumnIndex("_from"));
                bill.fromApp = cursor.getString(cursor.getColumnIndex("fromapp"));
                bill.price = cursor.getFloat(cursor.getColumnIndex("price"));
                bill.date = new Date(cursor.getLong(cursor.getColumnIndex("date")));
                bill.out = cursor.getInt(cursor.getColumnIndex("out")) == 1;
                bill.type = cursor.getInt(cursor.getColumnIndex("type"));
                bills.add(bill);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return bills;
    }

    public static List<App.Project> queryProject(String where, String[] args) {
        String orderBy = "createdate asc";
        List<App.Project> projects = new ArrayList<>();
        if (db == null) {
            return projects;
        }
        Cursor cursor = db.query("project", null, where, args, null, null, orderBy);
        if (cursor.moveToFirst()) {
            do {
                App.Project project = new App.Project();
                project.setCreateDate(new Date(cursor.getLong(cursor.getColumnIndex("createdate"))));
                project.setName(cursor.getString(cursor.getColumnIndex("name")));
                project.setFrom(cursor.getString(cursor.getColumnIndex("_from")));
                project.setPrice(cursor.getFloat(cursor.getColumnIndex("price")));
                project.setTimes(cursor.getInt(cursor.getColumnIndex("times")));
                project.getBills().clear();
                project.getBills().addAll(queryBills("_from=?", new String[]{project.getName()}));
                projects.add(project);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return projects;
    }

    public static List<App> queryApp(String where, String[] args) {
        List<App> apps = new ArrayList<>();
        if (db == null) {
            return apps;
        }
        Cursor cursor = db.query("app", null, where, args, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                App app = new App();
                app.setName(cursor.getString(cursor.getColumnIndex("name")));
                app.setCreateBillDay(cursor.getInt(cursor.getColumnIndex("createbillday")));
                app.setPayBillDay(cursor.getInt(cursor.getColumnIndex("paybillday")));
                app.getProjects().clear();
                app.getProjects().addAll(queryProject("_from=?", new String[]{app.getName()}));
                apps.add(app);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return apps;
    }

    public static List<Person.CycleProject> queryCycleProject(String where, String[] args) {
        List<Person.CycleProject> projects = new ArrayList<>();
        if (db == null) {
            return projects;
        }
        Cursor cursor = db.query("cycle_project", null, where, args, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Person.CycleProject project = new Person.CycleProject();
                project.setName(cursor.getString(cursor.getColumnIndex("name")));
                project.setPrice(cursor.getFloat(cursor.getColumnIndex("price")));
                project.setDay(cursor.getInt(cursor.getColumnIndex("day")));
                project.setOut(cursor.getInt(cursor.getColumnIndex("out")) == 1);
                projects.add(project);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return projects;
    }
}
