package com.serialize;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ziyuntest.R;

public class FirstActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn;
    private TextView textView;
    private Context mContext;
    private Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        mContext = this.getApplicationContext();
        book = new Book("1", "Android", 78.0, "Taylor");
        textView = (TextView)findViewById(R.id.first_tv);
        textView.setText(book.toString());
        btn = (Button)findViewById(R.id.first_btn);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(mContext, SecondActivity.class);
        intent.putExtra(SecondActivity.SINGLE_SERIAL, book);
        startActivity(intent);

    }
}
