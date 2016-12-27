package com.example.administrator.moonlightcalendar.Util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/12/9 0009.
 */

public class MoonLightDBHelper extends SQLiteOpenHelper {

    private static final String TAG = "MoonLightSQL";

    public MoonLightDBHelper(Context context) {
        super(context, "MoonLight.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.beginTransaction();
        try {
            db.execSQL("CREATE TABLE app(name TEXT PRIMARY KEY," +
                    "paybillday INTEGER," +
                    "createbillday INTEGER)");
            db.execSQL("CREATE TABLE project(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT," +
                    "_from TEXT," +
                    "price REAL," +
                    "createdate INTEGER," +
                    "times INTEGER)");
            db.execSQL("CREATE TABLE cycle_project(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name REAL," +
                    "price REAL," +
                    "out INTEGER," +
                    "day INTEGER)");
            db.execSQL("CREATE TABLE bill(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "_from TEXT," +
                    "fromapp TEXT," +
                    "price REAL," +
                    "date INTEGER," +
                    "out INTEGER," +
                    "pID INTEGER," +
                    "type INTEGER)");
            db.setTransactionSuccessful();
        }
        finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
