package com.viewevent;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ziyuntest.R;

public class ViewEvent extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "ziyun";
    private static Context mContext;
    private TextView textView;
    private Button button;
    private View decor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);
        mContext = this.getApplicationContext();
        initView();
    }

    private void initView() {
        decor = this.getWindow().getDecorView();
        textView = (TextView)findViewById(R.id.view_tv);
        button = (Button)findViewById(R.id.view_btn);
        button.setOnClickListener(this);
    }

    @Override
    public void onUserInteraction() {
        Log.d(TAG, "Method: onUserInteraction");
    }

    @Override
    protected void onUserLeaveHint() {
        Log.d(TAG, "Method: onUserLeaveHint");
    }

    @Override
    public void onClick(View v) {

    }
}
