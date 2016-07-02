package com.fragment;

import android.content.res.Configuration;
import android.os.Bundle;

/**
 * Created by Ziyun on 2016/6/29.
 */
public class DetailActivity extends android.support.v4.app.FragmentActivity {
	private static final String TAG = "Ziyun";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 横屏模式时，无需显示该activity
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			finish();
			return;
		}
		if (savedInstanceState == null) {
			DetailFragment detailFragment = DetailFragment.newInstance(getIntent().getExtras().getString(
					TitleFragment.CUR_TITLE));
            getSupportFragmentManager().beginTransaction().add(android.R.id.content, detailFragment).commit();
		}
	}
}
