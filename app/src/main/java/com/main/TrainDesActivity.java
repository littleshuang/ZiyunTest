package com.main;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * Created by Ziyun on 2016/6/29.
 * 当屏幕为竖屏时，需要通过该activity在新页面显示详情信息
 * 该activity完成的工作只是简单地加载TrainDesFragment即可
 */
public class TrainDesActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        // 当屏幕处于横屏模式时，无需显示该Activity
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            finish();
            return;
        }

		if (savedInstanceState == null){
            // 当无已保存的状态信息时，加载显示内容的片段TrainDesFragment
            TrainDesFragment fragment = TrainDesFragment.newInstance(getIntent().getExtras().getInt(
                    TrainTitleFragment.ARG_INTENT_INDEX));
            getFragmentManager().beginTransaction().add(android.R.id.content, fragment).commit();
        }
	}
}
