package com.draw;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Ziyun on 2016/7/1.
 */
public class HandDrawView extends View {

	float preX;
	float preY;
	private Path path;
	public Paint paint = null;
	final int WIDTH = 800;
	final int HEIGHT = 1080;
	Canvas mCanvas = null;
	Bitmap cacheBitmap = null;
	Canvas cacheCanvas = null;

	public HandDrawView(Context context, AttributeSet set) {
		super(context, set);

		paint = new Paint();
		path = new Path();
		cacheBitmap = Bitmap.createBitmap(WIDTH, HEIGHT, Bitmap.Config.ARGB_8888);
		cacheCanvas = new Canvas();
		cacheCanvas.setBitmap(cacheBitmap);

		// 设置画笔
		paint.setColor(0xaaff4081);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(2);
		paint.setAntiAlias(false); // 抗锯齿
		paint.setDither(true); // 平滑处理
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		// 获取点击事件的位置
		float eventX = event.getX();
		float eventY = event.getY();

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			path.moveTo(eventX, eventY);
			preX = eventX;
			preY = eventY;
			break;

		case MotionEvent.ACTION_MOVE:
			path.quadTo(preX, preY, eventX, eventY); // 在两点间作一条曲线
			preX = eventX;
			preY = eventY;
			break;

		case MotionEvent.ACTION_UP:
			cacheCanvas.drawPath(path, paint);
			path.reset();
			break;
		}
		invalidate();
		return true;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Paint bitmapPaint = new Paint();
		mCanvas = canvas;
		canvas.drawColor(Color.WHITE);
		canvas.drawBitmap(cacheBitmap, 0, 0, bitmapPaint);
		canvas.drawPath(path, paint);
	}

	// 清空画布内容
	public void clearCanvas() {
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
		cacheCanvas.drawPaint(paint);
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
		invalidate();
	}

	/**
	 * 将当前画布内容保存为图片
	 * 
	 * @param fileName
	 *            ：文件名
	 * @throws FileNotFoundException
	 */
	public void saveToFile(String fileName) throws FileNotFoundException {
		File file = new File(fileName);
		if (file.exists()) {
			throw new RuntimeException("File " + fileName + " is already exists!");
		}
		try {
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);
			cacheBitmap.compress(Bitmap.CompressFormat.PNG, 50, fos); // 将当前的bitmap图片压缩成为其他格式
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
