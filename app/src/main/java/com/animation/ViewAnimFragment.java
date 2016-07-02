package com.animation;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.ziyuntest.R;

/**
 * A simple class for showing the use of tween animation
 */
public class ViewAnimFragment extends Fragment implements View.OnClickListener {
	private static String TAG = "Ziyun";    //For log
	private static Context mContext;        // The context from the attaching activity
	private Animation mAnimation;
	private ImageView image;
	private Button btn1;
	private Button btn2;
	private Button btn3;
	private Button btn4;
	private Button btn5;

	public ViewAnimFragment() {
	}

	public static ViewAnimFragment newInstance(Context ctx) {
		ViewAnimFragment fragment = new ViewAnimFragment();
		mContext = ctx;
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_view_anim, container, false);
		initView(view);
		if (mContext == null) {
			Log.e(TAG, "mContext can not be null");
		}
		return view;
	}

	private void initView(View v) {
		btn1 = (Button) v.findViewById(R.id.view_anim_btn1);
		btn2 = (Button) v.findViewById(R.id.view_anim_btn2);
		btn3 = (Button) v.findViewById(R.id.view_anim_btn3);
		btn4 = (Button) v.findViewById(R.id.view_anim_btn4);
		btn5 = (Button) v.findViewById(R.id.view_anim_btn5);
		image = (ImageView) v.findViewById(R.id.view_anim_iv1);

		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		btn3.setOnClickListener(this);
		btn4.setOnClickListener(this);
		btn5.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.view_anim_btn1:
			translate();
			break;

		case R.id.view_anim_btn2:
            rotate();
			break;

		case R.id.view_anim_btn3:
            scale();
			break;

		case R.id.view_anim_btn4:
            alpha();
			break;

		case R.id.view_anim_btn5:
            // This is showing the using of how to use animation by xml
			if (mAnimation != null) {
				mAnimation.cancel();
			}
			if (mContext == null) {
				Log.e(TAG, "mContext can not be null");
				Toast.makeText(getContext(), "mContext can not be null", Toast.LENGTH_SHORT).show();
			} else {
				mAnimation = AnimationUtils.loadAnimation(mContext, R.anim.animation1);
				image.startAnimation(mAnimation);
                Toast.makeText(mContext, "xml", Toast.LENGTH_SHORT).show();
			}
			break;
		}
	}

    // The method for showing the use of AlphaAnimation
    private void alpha() {
        AlphaAnimation alpha1 = new AlphaAnimation(1, 0);
        alpha1.setDuration(1000);
        alpha1.setRepeatMode(Animation.REVERSE);
        alpha1.setRepeatCount(5);
        mAnimation = alpha1;
        image.startAnimation(mAnimation);
    }

    // The method for showing the use of ScaleAnimation
    private void scale() {
        ScaleAnimation scale1 = new ScaleAnimation(1, 1.5f, 1, 1.5f);
        final ScaleAnimation scale2 = new ScaleAnimation(1, 1.5f, 1, 1.5f, Animation.RELATIVE_TO_PARENT, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scale2.setFillAfter(true);
        if (mAnimation != null)
            mAnimation.cancel();
        scale1.setDuration(1000);
        scale1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                showToast("Start scale");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                scale2.setDuration(2000);
                mAnimation = scale2;
                image.startAnimation(mAnimation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mAnimation = scale1;
        image.startAnimation(mAnimation);
    }

    // The method for showing the use of RotateAnimation
    private void rotate() {
        final RotateAnimation rotate1 = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 1f);
        final RotateAnimation rotate2 = new RotateAnimation(0, 90, Animation.RELATIVE_TO_PARENT, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        if (mAnimation != null)
            mAnimation.cancel();
        mAnimation = rotate1;
        mAnimation.setDuration(1000);
        rotate1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                showToast("Start rotate");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (mAnimation != null)
                    mAnimation.cancel();
                mAnimation = rotate2;
                mAnimation.setDuration(1000);
                image.startAnimation(mAnimation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        image.startAnimation(mAnimation);
    }

    // The method for showing the use of TranslateAnimation
    private void translate() {
        TranslateAnimation translate1 = new TranslateAnimation(0, 120, 0, 100);
        translate1.setFillAfter(true);
        final TranslateAnimation translate2 = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.3f,
                Animation.RELATIVE_TO_PARENT, 0.5f, Animation.RELATIVE_TO_SELF, 3,
                Animation.RELATIVE_TO_SELF, 5);
        if (mAnimation != null)
            mAnimation.cancel();
        translate1.setDuration(1000);
        translate1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                showToast("Start");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                showToast("End");
                mAnimation.cancel();
                mAnimation = translate2;
                mAnimation.setDuration(2000);
                image.startAnimation(mAnimation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mAnimation = translate1;
        image.startAnimation(mAnimation);
    }

    private void showToast(String str){
        Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
    }

    private void animationSet(){
        AnimationSet as = new AnimationSet(true);   // 动画集共享插值器
        as.setDuration(3000);

        AlphaAnimation aa = new AlphaAnimation(0, 1);
        aa.setDuration(1000);
        as.addAnimation(aa);

        TranslateAnimation ta = new TranslateAnimation(0, 100, 0, 100);
        ta.setDuration(1000);
        as.addAnimation(ta);

        image.startAnimation(as);
    }
}
