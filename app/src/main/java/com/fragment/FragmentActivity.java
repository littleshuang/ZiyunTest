package com.fragment;

import android.os.Bundle;

import com.ziyuntest.R;

/**
 * Created by Ziyun on 2016/6/28.
 */
public class FragmentActivity extends android.support.v4.app.FragmentActivity {
    private static final String TAG = "Ziyun";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
    }
}
