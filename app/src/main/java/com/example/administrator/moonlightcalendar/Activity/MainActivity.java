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
import com.example.administrator.moonlightcalendar.Util.myUtil.MoonLightDBUtil;
import com.example.administrator.moonlightcalendar.adapter.CalendarAdapter;
import com.example.administrator.moonlightcalendar.model.App;
import com.example.administrator.moonlightcalendar.model.Bill;
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
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Bill> bills = MoonLightDBUtil.queryBills("_from=?", new String[]{"zxc工资"}, null);
                if (bills.isEmpty()) {
                    Person.getInstance().createLifeCost(false, 10000, "zxc工资", java.sql.Date.valueOf("2017-1-21"));
                }
                else {
                    MoonLightDBUtil.deleteBills(bills.get(0));
                }
                refresh();
            }
        });

        initView();
        initData();
    }

    private void addData() {
        MoonLightDBUtil.clear();

        Person person = Person.getInstance();
        person.setPayEachDay(70);
        person.setOriginWealth(4177);
        App ant = person.createNewApp("蚂蚁花呗", 1, 10);
        ant.createProject("账单分期", java.sql.Date.valueOf("2016-6-20"), 1326.2f, 12);
        ant.createProject("账单分期", java.sql.Date.valueOf("2016-10-20"), 739.3f, 6);
        ant.createProject("12月剁手", java.sql.Date.valueOf("2016-12-20"), 360.3f, 1);
        ant.createProject("1月剁手", java.sql.Date.valueOf("2017-1-20"), 491.7f, 1);
        ant.createProject("他的12月剁手", java.sql.Date.valueOf("2016-12-20"), 572f, 1);
        ant.createProject("他的2月剁手", java.sql.Date.valueOf("2017-2-20"), 950f, 6);

        App jd = person.createNewApp("京东", 0, 0);
        jd.createProject("灶台", java.sql.Date.valueOf("2016-12-17"), 99f, 6);
        jd.createProject("挂钟", java.sql.Date.valueOf("2016-12-15"), 104f, 1);
        jd.createProject("美厨", java.sql.Date.valueOf("2016-12-17"), 287.9f, 6);
        jd.createProject("咖啡桌", java.sql.Date.valueOf("2016-12-17"), 175.08f, 6);

        App fql = person.createNewApp("分期乐", 10, 20);
        fql.createProject("iphone", java.sql.Date.valueOf("2016-5-24"), 5707.9f, 12);
        fql.createProject("借款", java.sql.Date.valueOf("2016-11-6"), 3100f, 6);
        fql.createProject("借款", java.sql.Date.valueOf("2016-11-11"), 750f, 3);
        fql.createProject("借款", java.sql.Date.valueOf("2016-11-14"), 1250f, 3);
        fql.createProject("借款", java.sql.Date.valueOf("2016-12-29"), 1500f, 3);
        fql.createProject("ticwatch", java.sql.Date.valueOf("2016-11-17"), 1728.6f, 12);
        fql.createProject("kindle", java.sql.Date.valueOf("2016-11-24"), 958f, 12);

        App rent = person.createNewApp("蚂蚁借呗", 21, 6);
        rent.createProject("借款", java.sql.Date.valueOf("2016-9-6"), 525, 6);
        rent.createProject("借款", java.sql.Date.valueOf("2016-9-7"), 2100, 6);
        rent.createProject("借款", java.sql.Date.valueOf("2016-10-7"), 525, 6);
        rent.createProject("借款", java.sql.Date.valueOf("2016-11-23"), 889, 6);

        App card = person.createNewApp("信用卡", 25, 5);
        card.createProject("账单分期", java.sql.Date.valueOf("2016-10-3"), 5235.8f, 6);
        card.createProject("1月账单", java.sql.Date.valueOf("2016-1-11"), 196.8f, 1);

        person.createCycleProject("房租", 2500f, 12, true);
        person.createCycleProject("英语", 1300f, 15, true);
        person.createCycleProject("工资", 6000f, 8, false);
        person.createCycleProject("他的工资", 2300f, 25, false);

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
//        addData();
//        refresh();
    }

    public void refresh() {
        DataSource.getInstance().refreshFinance();
        mCalendarView.getAdapter().notifyDataSetChanged();
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
        Intent intent = new Intent(this, FinanceActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("finance", finance);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
