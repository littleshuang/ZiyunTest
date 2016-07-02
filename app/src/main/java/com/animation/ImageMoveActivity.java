package com.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.utils.TintedBitmapDrawable;
import com.ziyuntest.R;

public class ImageMoveActivity extends AppCompatActivity {
	private ImageView flower;
	private ImageView cake;
	private Button btn1;
	private Button btn2;
	private Button btn3;
	private Button btn4;
	private Context mContext;
	private boolean cakeLighting = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_move);
		mContext = this;

		init();
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null)
						.show();
			}
		});
	}

	private void init() {
		flower = (ImageView) findViewById(R.id.flower_iv);
		cake = (ImageView)findViewById(R.id.cake_iv);
		btn1 = (Button) findViewById(R.id.btn1);
		btn2 = (Button) findViewById(R.id.btn2);
		btn3 = (Button) findViewById(R.id.btn3);
		btn4 = (Button) findViewById(R.id.btn4);

		TintedBitmapDrawable drawable = new TintedBitmapDrawable(mContext.getResources(), R.drawable.flower,
				R.color.colorAccent);
		drawable.setTint(getResources().getColor(R.color.colorAccent));
		flower.setImageDrawable(drawable);

		btn1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				lightCake();
			}
		});

		btn2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				scale();
			}
		});

		btn3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				rotate();
			}
		});

		btn4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				for (int i = 0; i < 10; ++i){
					infinite();
				}
			}
		});
	}

	private void infinite() {
		float width = getWindowManager().getDefaultDisplay().getWidth() - flower.getWidth() * 2;
		float height = getWindowManager().getDefaultDisplay().getHeight() - flower.getHeight() * 4;
		AnimatorSet set = new AnimatorSet();
		ObjectAnimator animator1 = ObjectAnimator.ofFloat(flower, "translationX", width);
		ObjectAnimator animator2 = ObjectAnimator.ofFloat(flower, "translationY", height);
		ObjectAnimator animator3 = ObjectAnimator.ofFloat(flower, "translationX", 0);
		ObjectAnimator animator4 = ObjectAnimator.ofFloat(flower, "translationY", 0);
//		animator1.setRepeatCount(-1);
//		animator2.setRepeatCount(-1);
//		animator3.setRepeatCount(-1);
//		animator4.setRepeatCount(-1);
		set.setDuration(1000);
		set.play(animator1);
		set.play(animator2).after(animator1);
		set.play(animator3).after(animator2);
		set.play(animator4).after(animator3);
		set.start();
		set.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				infinite();
			}
		});
	}

	private void inf(){
		float width = getWindowManager().getDefaultDisplay().getWidth() - flower.getWidth() * 2;
		float height = getWindowManager().getDefaultDisplay().getHeight() - flower.getHeight() * 4;
		PropertyValuesHolder pvh1 = PropertyValuesHolder.ofFloat("translationX", width);
		PropertyValuesHolder pvh2 = PropertyValuesHolder.ofFloat("translationY", height);
		PropertyValuesHolder pvh3 = PropertyValuesHolder.ofFloat("translationX", 0);
		PropertyValuesHolder pvh4 = PropertyValuesHolder.ofFloat("translationY", 0);
		ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(flower, pvh1, pvh2, pvh3, pvh4);
		animator.setRepeatCount(-1);
		animator.start();
	}

	private void translate() {
		float screenHeight = getWindowManager().getDefaultDisplay().getHeight();
		ObjectAnimator translateAnim = ObjectAnimator.ofFloat(flower, "translationY", screenHeight
				- flower.getHeight() * 4);
        translateAnim.setDuration(1000);
        translateAnim.start();
	}

	private void scale(){
		PropertyValuesHolder pvh1 = PropertyValuesHolder.ofFloat("scaleX", 1f, 1.5f);
		PropertyValuesHolder pvh2 = PropertyValuesHolder.ofFloat("scaleY", 1f, 1.5f);
		ObjectAnimator scale = ObjectAnimator.ofPropertyValuesHolder(flower, pvh1, pvh2);
		scale.setDuration(1000);
		scale.start();
	}

	private void rotate(){
		PropertyValuesHolder pvh1 = PropertyValuesHolder.ofFloat("rotationX", 0, 360);
		PropertyValuesHolder pvh2 = PropertyValuesHolder.ofFloat("rotationY", 0, 360);
		PropertyValuesHolder pvh3 = PropertyValuesHolder.ofFloat("pivotX", 0);
		PropertyValuesHolder pvh4 = PropertyValuesHolder.ofFloat("pivotY", 0);
		PropertyValuesHolder pvh5 = PropertyValuesHolder.ofFloat("rotation", 360);
//		ObjectAnimator rotate = ObjectAnimator.ofPropertyValuesHolder(flower, pvh3, pvh4, pvh1);
		ObjectAnimator rotate = ObjectAnimator.ofPropertyValuesHolder(flower, pvh3, pvh4, pvh5);
		rotate.setDuration(1000);
		rotate.start();
	}

	private void lightCake(){
		ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 255);
		valueAnimator.setDuration(5000);
		valueAnimator.start();

		valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				int value = (int)animation.getAnimatedValue();
//				drawable.setTint(value);
//				cake.setImageDrawable(drawable);
				cake.setBackgroundColor(value);
				Snackbar.make(cake, "The color value is: " + value, Snackbar.LENGTH_LONG)
						.setAction("Action", null).show();
			}
		});
	}
}
