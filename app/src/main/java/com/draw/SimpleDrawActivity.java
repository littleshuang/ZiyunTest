package com.draw;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.ziyuntest.R;

public class SimpleDrawActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_draw);

        ActionBar toolbar = getSupportActionBar();
        toolbar.setTitle("基础绘图");
    }
}
