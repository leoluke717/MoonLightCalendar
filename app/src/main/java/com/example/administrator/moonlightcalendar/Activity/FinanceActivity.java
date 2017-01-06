package com.example.administrator.moonlightcalendar.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

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
    private Finance mFinance;

    public Finance getFinance() {
        return mFinance;
    }

    public void setFinance(Finance finance) {
        mFinance = finance;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();
        initData();
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
}
