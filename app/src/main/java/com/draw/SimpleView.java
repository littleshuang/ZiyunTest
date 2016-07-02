package com.draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Ziyun on 2016/7/1.
 * 
 * 这是一个简单的画图类，该类中包含一些简单的数学图形的绘制及画笔的基本设置
 */
public class SimpleView extends View {

	public SimpleView(Context context) {
		super(context);
	}

	public SimpleView(Context context, AttributeSet set) {
		super(context, set);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

        canvas.translate(50, 50);
		canvas.drawColor(Color.WHITE); // 画布颜色为白色
		Paint paint = new Paint(); // 画笔
		paint.setAntiAlias(true); // 设置画笔抗锯齿
		paint.setColor(0xffff4081); // 画笔颜色为枚红色
		paint.setAlpha(180); // 画笔透明度
		paint.setStrokeWidth(4); // 笔锋宽度
		paint.setStyle(Paint.Style.STROKE); // 填充风格：描边

		RectF rectF1 = new RectF(10, 10, 70, 70); // 扇形的矩形区域
		canvas.drawArc(rectF1, 0, 100, true, paint); // 绘制扇形，当useCenter=false时，为绘制弧形
		canvas.drawCircle(40f, 120f, 35f, paint); // 画圆
		canvas.drawLine(10, 170, 70, 230, paint); // 画直线
		float[] pts1 = { 10, 250, 70, 310, 15, 245, 20, 240, 25, 235, 30, 230 };
		canvas.drawPoints(pts1, paint); // 画点，每两个数字为一个点坐标
		float[] pts2 = { 10, 330, 70, 330, 10, 340, 70, 340, 10, 350, 70, 350, 10, 360, 70, 360 };
		canvas.drawLines(pts2, paint); // 画多条线，每四个数字决定两个点，即一条线
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			canvas.drawRoundRect(10, 410, 70, 470, 20, 20, paint); // 圆角矩形
			canvas.drawOval(10, 490, 70, 550, paint); // 椭圆

			Path path1 = new Path(); // 根据路径画图
			path1.moveTo(40, 570);
			path1.lineTo(10, 630);
			path1.lineTo(70, 630);
			path1.lineTo(40, 570);
			canvas.drawPath(path1, paint);
		}

		// 设置画笔填充风格为填充时作图
		paint.setStyle(Paint.Style.FILL_AND_STROKE);
        RectF rectF2 = new RectF(90, 10, 150, 70); // 扇形的矩形区域
        canvas.drawArc(rectF2, 0, 100, true, paint); // 绘制扇形，当useCenter=false时，为绘制弧形
        canvas.drawCircle(120f, 120f, 35f, paint); // 画圆
        canvas.drawLine(90, 170, 150, 230, paint); // 画直线
        float[] pts3 = { 90, 250, 150, 310, 95, 245, 100, 240, 105, 235, 110, 230 };
        canvas.drawPoints(pts3, paint); // 画点，每两个数字为一个点坐标
        float[] pts4 = { 90, 330, 150, 330, 90, 340, 150, 340, 90, 350, 150, 350, 90, 360, 150, 360 };
        canvas.drawLines(pts4, paint); // 画多条线，每四个数字决定两个点，即一条线
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawRoundRect(90, 410, 150, 470, 20, 20, paint); // 圆角矩形
            canvas.drawOval(90, 490, 150, 550, paint); // 椭圆

            Path path2 = new Path(); // 根据路径画图
            path2.moveTo(120, 570);
            path2.lineTo(90, 630);
            path2.lineTo(150, 630);
            path2.lineTo(120, 570);
            canvas.drawPath(path2, paint);
        }


        // 设置画笔阴影后作图
		Shader shader = new RadialGradient((float) (getWidth() / 2), (float) (getHeight() / 2), 5f, 0xeeff4081,
				0xee00ff00, Shader.TileMode.MIRROR);
//        Shader shader = new LinearGradient(0, 0, 40, 60, new int[]{Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW},
//                null, Shader.TileMode.REPEAT);
		paint.setShader(shader);
        paint.setShadowLayer(20, 10, 10, Color.GRAY);   // 注：radius取值为0-25

        RectF rectF3 = new RectF(170, 10, 230, 70); // 扇形的矩形区域
        canvas.drawArc(rectF3, 0, 100, true, paint); // 绘制扇形，当useCenter=false时，为绘制弧形
        canvas.drawCircle(200f, 120f, 35f, paint); // 画圆
        canvas.drawLine(170, 170, 230, 230, paint); // 画直线
        float[] pts5 = { 170, 250, 230, 310, 175, 245, 180, 240, 185, 235, 190, 230 };
        canvas.drawPoints(pts5, paint); // 画点，每两个数字为一个点坐标
        float[] pts6 = { 170, 330, 230, 330, 170, 340, 230, 340, 170, 350, 230, 350, 170, 360, 230, 360 };
        canvas.drawLines(pts6, paint); // 画多条线，每四个数字决定两个点，即一条线
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawRoundRect(170, 410, 230, 470, 20, 20, paint); // 圆角矩形
            canvas.drawOval(170, 490, 230, 550, paint); // 椭圆

            Path path3 = new Path(); // 根据路径画图
            path3.moveTo(200, 570);
            path3.lineTo(170, 630);
            path3.lineTo(230, 630);
            path3.lineTo(200, 570);
            canvas.drawPath(path3, paint);
        }

        // 绘制字符
        paint.setTextAlign(Paint.Align.CENTER);
		paint.setStrokeWidth(2);
        paint.setTextSize(30);
        paint.setShader(null);
        paint.setShadowLayer(0, 10, 10, Color.GRAY);

        canvas.drawText("扇形", 300, 80, paint);
        canvas.drawText("圆形", 300, 160, paint);
        canvas.drawText("直线", 300, 240, paint);
        canvas.drawText("点", 300, 320, paint);
        canvas.drawText("直线", 300, 400, paint);
        canvas.drawText("圆角矩形", 300, 460, paint);
        canvas.drawText("椭圆", 300, 540, paint);
        canvas.drawText("三角形", 300, 620, paint);
    }
}
