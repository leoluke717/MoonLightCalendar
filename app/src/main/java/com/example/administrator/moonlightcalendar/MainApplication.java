package com.example.administrator.moonlightcalendar;

import android.app.Application;
import android.content.Context;

import com.example.administrator.moonlightcalendar.model.DataSource;
import com.example.administrator.moonlightcalendar.model.Person;

/**
 * Created by Administrator on 2016/12/15 0015.
 */

public class MainApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        Person.getInstance();
    }

    public static Context getContext() {
        return context;
    }
}
