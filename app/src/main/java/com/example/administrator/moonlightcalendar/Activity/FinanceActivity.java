package com.example.administrator.moonlightcalendar.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.administrator.moonlightcalendar.R;
import com.example.administrator.moonlightcalendar.adapter.FinanceListAdapter;
import com.example.administrator.moonlightcalendar.model.Finance;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/1/6 0006.
 */

public class FinanceActivity extends BaseActivity {

    @BindView(R.id.financeList)
    RecyclerView mFinanceList;
    @BindView(R.id.text)
    TextView mText;
    private Finance mFinance;

    public Finance getFinance() {
        return mFinance;
    }

    public void setFinance(Finance finance) {
        mFinance = finance;
        mText.setText("" + finance.getTotalMoney());
        mText.setTextColor(finance.getFinanceColor());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initData();
        initView();
    }

    private void initData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        setFinance((Finance) bundle.getSerializable("finance"));
    }

    private void initView() {
        mFinanceList.setLayoutManager(new LinearLayoutManager(this));
        mFinanceList.setAdapter(new FinanceListAdapter(this, mFinance.getBillsMap()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                menuItemSelected(item);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void menuItemSelected(MenuItem item) {
    }
}
