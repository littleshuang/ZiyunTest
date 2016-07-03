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
import android.graphics.drawable.shapes.Shape;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;

import com.ziyuntest.R;

import java.util.ArrayList;

public class BouncingBallsActivity extends AppCompatActivity {
    private static float BALL_SIZE = 50F;
    private static int FULL_TIME = 1000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bouncing_balls);
		RelativeLayout container = (RelativeLayout) findViewById(R.id.container);
		container.addView(new BouncingBallView(this));
	}

	// 大珠小珠落玉盘界面
	private class BouncingBallView extends View implements ValueAnimator.AnimatorUpdateListener {
		private ArrayList<BounceHolder> balls = new ArrayList<>();

		public BouncingBallView(Context context) {
			super(context);
			setBackgroundColor(Color.WHITE);
			ObjectAnimator colorAnim1 = null;
			ObjectAnimator colorAnim2 = null;
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
				colorAnim1 = ObjectAnimator.ofArgb(this, "BackgroundColor", 0x00000000, 0xffff4081);

			}
			colorAnim1.setDuration(500);
			colorAnim1.setInterpolator(new AccelerateInterpolator());
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
				colorAnim2 = ObjectAnimator.ofArgb(this, "BackgroundColor", 0xffff4081, 0xffffffff);
			}
			colorAnim2.setDuration(500);
			colorAnim2.setInterpolator(new DecelerateInterpolator());
            AnimatorSet colorSet = new AnimatorSet();
            colorSet.play(colorAnim1).before(colorAnim2);
            colorSet.start();
		}

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float eventX = event.getX();
            float eventY = event.getY();
            if (event.getAction() != MotionEvent.ACTION_DOWN && event.getAction() != MotionEvent.ACTION_MOVE){
                return false;
            }
            float screenHeight = getHeight();
            float startY = eventY;
            float endY = screenHeight - BALL_SIZE;
            BounceHolder newBall = addBouceBall(eventX, eventY);
            int duration = 0;
            if (screenHeight - startY > 0){
                duration = (int)(FULL_TIME * (screenHeight - startY) / screenHeight);
            }

            // 下降动画
            ObjectAnimator fallAnim = ObjectAnimator.ofFloat(newBall, "Y", startY, endY);
            fallAnim.setDuration(duration);
            fallAnim.setInterpolator(new AccelerateInterpolator());
            fallAnim.addUpdateListener(this);

            // 左偏移动画
            ObjectAnimator leftAnim = ObjectAnimator.ofFloat(newBall, "X", newBall.getX(), newBall.getX() - BALL_SIZE / 2);
            leftAnim.setDuration(duration / 4);
            leftAnim.setRepeatCount(1);
            leftAnim.setRepeatMode(ValueAnimator.REVERSE);
            leftAnim.setInterpolator(new DecelerateInterpolator());

            // 宽度拉伸动画
            ObjectAnimator widthLarge = ObjectAnimator.ofFloat(newBall, "Width", BALL_SIZE, 2 * BALL_SIZE);
            widthLarge.setDuration(duration / 4);
            widthLarge.setRepeatCount(1);
            widthLarge.setRepeatMode(ValueAnimator.REVERSE);
            widthLarge.setInterpolator(new DecelerateInterpolator());

            // 下移动画
            ObjectAnimator downAnim = ObjectAnimator.ofFloat(newBall, "Y", endY, endY + BALL_SIZE / 2);
            downAnim.setDuration(duration / 4);
            downAnim.setRepeatCount(1);
            downAnim.setRepeatMode(ValueAnimator.REVERSE);
            downAnim.setInterpolator(new DecelerateInterpolator());

            // 高度压缩动画
            ObjectAnimator heightSmall = ObjectAnimator.ofFloat(newBall, "Height", BALL_SIZE, BALL_SIZE / 2);
            heightSmall.setDuration(duration / 4);
            heightSmall.setRepeatCount(1);
            heightSmall.setRepeatMode(ValueAnimator.REVERSE);
            heightSmall.setInterpolator(new DecelerateInterpolator());

            // 反弹动画
            ObjectAnimator reverse = ObjectAnimator.ofFloat(newBall, "Y", endY, startY);
            reverse.setDuration(duration);
            reverse.setInterpolator(new DecelerateInterpolator());
            reverse.addUpdateListener(this);

            // 隐退动画
            ObjectAnimator fadeAnim = ObjectAnimator.ofFloat(newBall, "Alpha", 1f, 0f);
            fadeAnim.setDuration(250);
            fadeAnim.setInterpolator(new AccelerateInterpolator());
            fadeAnim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    balls.remove(((ObjectAnimator) animation).getTarget());
                }
            });
            fadeAnim.addUpdateListener(this);

            AnimatorSet animSet1 = new AnimatorSet();
            AnimatorSet animSet2 = new AnimatorSet();
            animSet1.play(fallAnim);
            animSet1.play(leftAnim).after(fallAnim);
            animSet1.play(leftAnim).with(widthLarge);
            animSet1.play(leftAnim).with(downAnim);
            animSet1.play(reverse).after(leftAnim);
            animSet2.play(fadeAnim).after(animSet1);
            animSet2.start();
            return true;
        }

        private BounceHolder addBouceBall(float x, float y) {
            OvalShape cycle = new OvalShape();
            cycle.resize(BALL_SIZE, BALL_SIZE);
            ShapeDrawable drawable = new ShapeDrawable(cycle);
            BounceHolder ball = new BounceHolder(drawable);
            ball.setX(x - BALL_SIZE / 2);
            ball.setY(y - BALL_SIZE / 2);

            int red = (int)(Math.random() * 255);
            int green = (int)(Math.random() * 255);
            int blue = (int)(Math.random() * 255);
            int color = 0x88000000 | red << 16 | green << 8 | blue;
            int darkColor = 0x88000000 | red / 4 << 16 | green / 4 << 8 | blue / 4;
            RadialGradient gradient = new RadialGradient(37.5f, 12.5f, BALL_SIZE, color, darkColor, Shader.TileMode.CLAMP);

            Paint paint = drawable.getPaint();
            paint.setShader(gradient);
            ball.setPaint(paint);
            balls.add(ball);
            return ball;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            for(BounceHolder ball : balls){
                canvas.save();
                canvas.translate(ball.getX(), ball.getY());
                ball.getShape().draw(canvas);
                canvas.restore();
            }

        }

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            this.invalidate();
        }
    }

	private class BounceHolder {
        private float x = 0;
        private float y = 0;
        private ShapeDrawable shape;
        private float width = BALL_SIZE;
        private float height = BALL_SIZE;
        private int color;
        private RadialGradient gradient;
        private Paint paint;

        public BounceHolder(ShapeDrawable drawable){
            this.shape = drawable;
        }

        public ShapeDrawable getShape() {
            return shape;
        }

        public void setShape(ShapeDrawable shape) {
            this.shape = shape;
        }

        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }

        public float getWidth() {
            return shape.getShape().getWidth();
        }

        public void setWidth(float width) {
            Shape thisShape = shape.getShape();
            thisShape.resize(width, thisShape.getHeight());
        }

        public RadialGradient getGradient() {
            return gradient;
        }

        public void setGradient(RadialGradient gradient) {
            this.gradient = gradient;
        }

        public Paint getPaint() {
            return paint;
        }

        public void setPaint(Paint paint) {
            this.paint = paint;
        }

        public float getHeight() {
            return shape.getShape().getHeight();
        }

        public void setHeight(float height) {
            Shape thisShape = shape.getShape();
            thisShape.resize(thisShape.getWidth(), height);
        }

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }
    }
}
