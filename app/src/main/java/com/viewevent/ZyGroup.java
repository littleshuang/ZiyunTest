package com.viewevent;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by Ziyun on 2016/6/12.
 */
public class ZyGroup extends LinearLayout {
    private static final String TAG = "ziyun";
    private static final String CLASS = "ZyGroup";

    public ZyGroup(Context context) {
        super(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ZyGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public ZyGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ZyGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(TAG, "Class: " + CLASS +"\n Method: onInterceptTouchEvent");
        return super.onInterceptTouchEvent(ev);
    }
}
