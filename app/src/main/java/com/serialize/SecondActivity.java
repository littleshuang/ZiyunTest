package com.serialize;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.ziyuntest.R;

public class SecondActivity extends AppCompatActivity {
    public static final String SINGLE_SERIAL = "single sirial";
    public static final String SINGLE_PARCEL = "single parcel";

    private TextView secondTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        secondTv = (TextView)findViewById(R.id.second_tv);

        Intent intent = getIntent();
        if (intent != null){
            Object object = intent.getSerializableExtra(SINGLE_SERIAL);
            if (object != null && object instanceof Book){
                Book book = (Book)object;
                secondTv.setText(book.toString());
            }else {
                secondTv.setText("Sth went wrong!");
            }
        }
    }
}
