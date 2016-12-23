package com.example.administrator.moonlightcalendar.Activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.moonlightcalendar.R;
import com.example.administrator.moonlightcalendar.Util.MoonLightDBUtil;
import com.example.administrator.moonlightcalendar.model.DataSource;
import com.example.administrator.moonlightcalendar.model.Finance;
import com.example.administrator.moonlightcalendar.model.Person;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.calendar_text)
    TextView mCalendarText;
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

        MoonLightDBUtil.clear();
        Person person = Person.getInstance();
        person.setOriginWealth(1000);
        person.setPayEachDay(70);

        person.createCycleProject("house rent", 2100, 12, true);
        person.createCycleProject("payment", 6300, 8, false);
        person.createCycleProject("payment2", 1300, 25, false);

        person.createNewApp("ant flower", 1, 10);
        person.createNewApp("jd", 0, 0);
        person.createNewApp("happy flower", 10, 20);

        person.getApps().get(0).createProject("洗衣机", java.sql.Date.valueOf("2016-8-5"), 600, 6);
        person.getApps().get(1).createProject("沙发", java.sql.Date.valueOf("2016-10-31"), 1000, 12);
        person.getApps().get(2).createProject("电脑", java.sql.Date.valueOf("2016-7-15"), 8000, 6);

    }

    public void refresh() {
        DataSource.getInstance().refreshFinance();
        StringBuilder stringBuilder = new StringBuilder();
        for (List<Finance> finances : DataSource.getInstance().financesList) {
            for (Finance finance : finances) {
                if (!finance.isReadOnly()) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(finance.getDate());
                    stringBuilder.append(calendar.get(Calendar.YEAR) + "年");
                    int month = calendar.get(Calendar.MONTH)+1;
                    stringBuilder.append(month + "月\n");
                    break;
                }
            }
            for (Finance finance : finances) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(finance.getDate());
                int month = calendar.get(Calendar.MONTH)+1;
                stringBuilder.append(calendar.get(Calendar.YEAR) + "年" +
                        month + "月" +
                        calendar.get(Calendar.DAY_OF_MONTH) + "日");
                stringBuilder.append("，星期" + calendar.get(Calendar.DAY_OF_WEEK));
                stringBuilder.append("，总余额：" + finance.getTotalMoney());
                stringBuilder.append("，账单：" + finance.getBillsMoney());
                stringBuilder.append("\n");
            }
        }
        mCalendarText.setText(stringBuilder.toString());
    }

    private void initView() {

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
}
