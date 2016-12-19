package com.example.administrator.moonlightcalendar.Util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2016/12/6 0006.
 */

public class DateUtil {

    public static final int DAY_MILLIS = 24 * 60 * 60 * 1000;

    public static String convertDateToString(long time) {
        Date date = new java.sql.Date(time*1000);
        SimpleDateFormat format;
        if (System.currentTimeMillis() - time < DAY_MILLIS) {
            format = new SimpleDateFormat("HH:mm");
        } else {
           format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        }
        String dateStr = format.format(date);
        return dateStr;
    }

    public static boolean isTheSameDay(Date date1, Date date2) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);
        int year1 = calendar.get(Calendar.YEAR);
        int month1 = calendar.get(Calendar.MONTH);
        int day1 = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.setTime(date2);
        int year2 = calendar.get(Calendar.YEAR);
        int month2 = calendar.get(Calendar.MONTH);
        int day2 = calendar.get(Calendar.DAY_OF_MONTH);

        return day1 == day2 && month1 == month2 && year1 == year2;
    }

    public static int DifferOfDays(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2) {
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                //闰年
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) {
                    timeDistance += 366;
                } else {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2 - day1);
        } else {
            System.out.println("判断day2 - day1 : " + (day2 - day1));
            return day2 - day1;
        }
    }
}
