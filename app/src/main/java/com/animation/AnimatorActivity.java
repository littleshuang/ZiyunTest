package com.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.ziyuntest.R;

import java.util.ArrayList;

public class AnimatorActivity extends AppCompatActivity {
	public static final float BALL_SIZE = 50F; // 小球的大小
	public static final int FULL_TIME = 1000; // 小球从顶部下降到屏幕底部所需时间

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_animator);
		RelativeLayout view = (RelativeLayout) findViewById(R.id.container);
		view.addView(new MyCycleView(this));
	}

	private class MyCycleView extends View implements ValueAnimator.AnimatorUpdateListener {
		public ArrayList<CycleShaper> balls = new ArrayList<>();

		public MyCycleView(Context context) {
			super(context);
			setBackgroundColor(Color.WHITE);
		}

		@Override
		public boolean onTouchEvent(MotionEvent event) {
			float eventX = event.getX();
			float eventY = event.getY();
			if (event.getAction() != MotionEvent.ACTION_DOWN && event.getAction() != MotionEvent.ACTION_MOVE) {
				return false;
			}

			CycleShaper newBall = addBall(eventX, eventY);
			float screenHeight = getHeight();
			float startY = eventY; // 小球下落的起始坐标
			float endY = screenHeight - BALL_SIZE; // 小球下落的终点坐标
			int duration = 0;
			if (screenHeight - startY > 0){
				duration = (int) (FULL_TIME * (screenHeight - startY) / screenHeight); // 下落时间
			}
			ObjectAnimator animator = ObjectAnimator.ofFloat(newBall, "CycleY", startY, endY); // 创建下落动画
			animator.setDuration(duration); // 设置动画持续时间
			animator.addUpdateListener(this); // 添加动画监听器

			ObjectAnimator fadeAnim = ObjectAnimator.ofFloat(newBall, "Alpha", 1f, 0f); // 设置隐退动画
			fadeAnim.setDuration(250);
            fadeAnim.addUpdateListener(this);
			fadeAnim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    balls.remove(((ObjectAnimator) animation).getTarget()); // 隐退动画结束时，将该小球从小球列表中删除
                }
            });

            AnimatorSet set = new AnimatorSet();
            set.play(animator).before(fadeAnim);
            set.start();
			return true;
		}

		@Override
		public void onAnimationUpdate(ValueAnimator animation) {
            this.invalidate();
		}

        @Override
        protected void onDraw(Canvas canvas) {
            for (CycleShaper cycle : balls){
                canvas.save();
                canvas.translate(cycle.getCycleX(), cycle.getCycleY());   // 变换画布坐标原点，便于作图
                cycle.getDrawable().draw(canvas);       // 将当前内容添加到画布上
                canvas.restore();   // 恢复坐标系
            }
        }

        private CycleShaper addBall(float x, float y) {

			OvalShape cycle = new OvalShape();
			cycle.resize(BALL_SIZE, BALL_SIZE);
			ShapeDrawable drawable = new ShapeDrawable(cycle);
			CycleShaper cur_ball = new CycleShaper(drawable);
			cur_ball.setCycleX(x - BALL_SIZE / 2);
			cur_ball.setCycleY(y - BALL_SIZE / 2);
			cur_ball.setCycleSize(BALL_SIZE);

			// 随机生成小球的颜色值
			int red = (int) (Math.random() * 255);
			int green = (int) (Math.random() * 255);
			int blue = (int) (Math.random() * 255);
			int color = 0xff000000 | red << 16 | green << 8 | blue; // 将随机生成的RGB颜色值转换为颜色
			Paint paint = drawable.getPaint();
			int darkColor = 0xff000000 | red / 4 << 16 | green / 4 << 8 | blue / 4;
			RadialGradient gradient = new RadialGradient(37.5f, 12.5f, BALL_SIZE, color, darkColor,
					Shader.TileMode.CLAMP);
			paint.setShader(gradient);
			cur_ball.setPaint(paint);
			balls.add(cur_ball);
			return cur_ball;
		}
	}

	private class CycleShaper {
		private ShapeDrawable drawable;
		private float cycleX;
		private float cycleY;
		private float cycleSize;
		private int color;
		private Paint paint;

		public CycleShaper(ShapeDrawable drawable) {
			this.drawable = drawable;
			cycleX = 0;
			cycleY = 0;
			cycleSize = BALL_SIZE;
			color = 0xffff4081;
			paint = new Paint();
		}

		public ShapeDrawable getDrawable() {
			return drawable;
		}

		public void setDrawable(ShapeDrawable drawable) {
			this.drawable = drawable;
		}

		public float getCycleX() {
			return cycleX;
		}

		public void setCycleX(float cycleX) {
			this.cycleX = cycleX;
		}

		public float getCycleY() {
			return cycleY;
		}

		public void setCycleY(float cycleY) {
			this.cycleY = cycleY;
		}

		public float getCycleSize() {
			return cycleSize;
		}

		public void setCycleSize(float cycleSize) {
			this.cycleSize = cycleSize;
		}

		public int getColor() {
			return color;
		}

		public void setColor(int color) {
			this.color = color;
		}

		public Paint getPaint() {
			return paint;
		}

		public void setPaint(Paint paint) {
			this.paint = paint;
		}
	}
}
