package com.example.administrator.moonlightcalendar.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.moonlightcalendar.Util.Utils;

/**
 * Created by Administrator on 2016/12/20 0020.
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.init(this);
    }
}
