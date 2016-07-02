package com.animation;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ziyuntest.R;

/**
 * Created by Ziyun on 2016/6/30.
 */
public class ValueAnimActivity extends Activity implements View.OnClickListener {
	private Button btn1;
	private Button btn2;
	private TextView tv1;
	private Context mContext;
	private float FULL_TIME = 2000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_valueanim);
		mContext = this;

		btn1 = (Button) findViewById(R.id.valueanim_btn1);
		btn2 = (Button) findViewById(R.id.valueanim_btn2);
		tv1 = (TextView) findViewById(R.id.valueanim_tv1);
		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.valueanim_btn1:
			changeTextColorByObject();
			break;

		case R.id.valueanim_btn2:
			addFlower();
			break;
		}
	}

	private void addFlower() {
		tv1.setVisibility(View.GONE);
		final LinearLayout container = (LinearLayout) findViewById(R.id.valueanim_container);
		container.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() != MotionEvent.ACTION_DOWN && event.getAction() != MotionEvent.ACTION_MOVE) {
					return false;
				}
				final ImageView imageView = new ImageView(mContext);
				imageView.setX(event.getX());
				imageView.setY(event.getY());
				imageView.setImageResource(R.drawable.flower);
				imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
						ViewGroup.LayoutParams.WRAP_CONTENT));
				ValueAnimator animator = ObjectAnimator.ofFloat(imageView, "Y", event.getY(), container.getHeight()
						- imageView.getHeight());
				animator.setDuration((long) FULL_TIME);
				animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
					@Override
					public void onAnimationUpdate(ValueAnimator animation) {
						container.addView(imageView);
						container.invalidate();
					}
				});
				animator.start();
				return true;
			}

		});

	}

	// 采用ObjectAnimator改变TextView的文字颜色
	private void changeTextColorByObject() {
		ObjectAnimator changeColor = null;
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
			changeColor = ObjectAnimator.ofArgb(tv1, "TextColor", 0xffff0000, 0xff00ff00); // 注意颜色值
		}
		changeColor.setDuration(3000);
		changeColor.start();
	}

	// 采用ValueAnimator改变TextView的文字颜色
	private void changeTextColorByValue() {
		ValueAnimator valueAnimator = null;
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
			valueAnimator = ValueAnimator.ofArgb(0xffff0000, 0xff00ff00);
		}
		valueAnimator.setDuration(3000);
		valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				tv1.setTextColor((Integer) animation.getAnimatedValue());
			}
		});
		valueAnimator.start();
	}

}
