package com.example.administrator.moonlightcalendar.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.moonlightcalendar.R;
import com.example.administrator.moonlightcalendar.adapter.CalendarAdapter;
import com.example.administrator.moonlightcalendar.model.DataSource;
import com.example.administrator.moonlightcalendar.model.Finance;
import com.example.administrator.moonlightcalendar.model.Person;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements RecyclerView.RecyclerListener, CalendarAdapter.onItemClickListener {

    public static final String TAG = "MainActivity";

    @BindView(R.id.calendar_view)
    RecyclerView mCalendarView;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Date date = java.sql.Date.valueOf("2016-12-1");
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refresh();
            }
        });

        initView();
        initData();
    }

    private void initData() {
//        Person person = Person.getInstance();
//        person.setOriginWealth(1000);
//        person.setPayEachDay(70);
//
//        person.createCycleProject("house rent", 2100, 12, true);
//        person.createCycleProject("payment", 6300, 8, false);
//        person.createCycleProject("payment2", 1300, 25, false);
//
//        person.createNewApp("ant flower", 1, 10);
//        person.createNewApp("jd", 0, 0);
//        person.createNewApp("happy flower", 10, 20);
//
//        person.getApps().get(0).createProject("洗衣机", java.sql.Date.valueOf("2016-8-5"), 600, 6);
//        person.getApps().get(1).createProject("沙发", java.sql.Date.valueOf("2016-10-31"), 1000, 12);
//        person.getApps().get(2).createProject("电脑", java.sql.Date.valueOf("2016-7-15"), 8000, 6);

    }

    public void refresh() {
        DataSource.getInstance().refreshFinance();
        Person person = Person.getInstance();
    }


    private void initView() {
        mCalendarView.setLayoutManager(new LinearLayoutManager(this));
        mCalendarView.setRecyclerListener(this);
        CalendarAdapter adapter = new CalendarAdapter(this, DataSource.getInstance().financesList);
        adapter.setOnItemClickListener(this);
        mCalendarView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {

    }

    @Override
    public void onClick(int section, int position) {
        List<List<Finance>> lists = DataSource.getInstance().financesList;
        Finance finance = lists.get(section).get(position);
        finance.fillList();
        Log.d(TAG, "onClick: section:"+section+" "+"position:"+position);
    }
}
