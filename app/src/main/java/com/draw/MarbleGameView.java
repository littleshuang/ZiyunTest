package com.draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import java.util.Random;

/**
 * Created by Ziyun on 2016/7/2.
 */
public class MarbleGameView extends View{
    private Paint paint = new Paint();
    private static Random random = new Random();
    // 小球信息
    private final int BALL_SIZE = 12;   // 小球大小
    private int ballX = random.nextInt(200) + 20;   // 小球初始水平位置
    private int ballY = random.nextInt(20) + 20;    // 小球初始垂直位置
    // 球拍信息
    private final int RACKET_WIDTH = 70;    // 球拍宽度
    private final int RACKET_HEIGHT = 20;   // 球拍高度
    private static int racketX;    // 球拍水平位置
    private static int racketY;    // 球拍垂直位置
    // 屏幕信息
    private int screenWidth;
    private int screenHeight;
    private boolean gameOver = false;   // 判断游戏是否结束
    private boolean finishGame = false; // 判断是否结束游戏
    private Canvas mCanvas;

    public MarbleGameView(Context context){
        super(context);
        setFocusable(true);
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public void setBallX(int ballX) {
        this.ballX = ballX;
    }

    public void setBallY(int ballY) {
        this.ballY = ballY;
    }

    public static void setRacketX(int racketX) {
        MarbleGameView.racketX = racketX;
    }

    public static void setRacketY(int racketY) {
        MarbleGameView.racketY = racketY;
    }

    public static MarbleGameView newInstance(Context context, int screenWidth, int screenHeight){
        MarbleGameView view = new MarbleGameView(context);
        view.screenWidth = screenWidth;
        view.screenHeight = screenHeight;
        racketX = random.nextInt(screenWidth) + 50;
        racketY = screenHeight - 180;
        return view;
    }

    public void setGameOver(boolean over){
        this.gameOver = over;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mCanvas = canvas;
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        if (gameOver){
            paint.setColor(Color.RED);
            paint.setTextSize(50);
            canvas.drawText("Game over", screenWidth / 2 - 150, screenHeight / 2, paint);
        }else if(finishGame){
            paint.setColor(Color.RED);
            paint.setTextSize(50);
            canvas.drawText("Finish game", screenWidth / 2 - 150, screenHeight / 2, paint);
        }else {
            // 绘制小球
            paint.setStrokeWidth(50);
            paint.setColor(0xffff4081);
            canvas.drawCircle(ballX, ballY, BALL_SIZE, paint);
            // 绘制球拍
            paint.setColor(0xff009688);
            canvas.drawRect(racketX, racketY, racketX + RACKET_WIDTH, racketY + RACKET_HEIGHT, paint);
        }
    }

    public int getBallY() {
        return ballY;
    }

    public int getBallX() {
        return ballX;
    }

    public int getRacketX() {
        return racketX;
    }

    public int getRacketY() {
        return racketY;
    }

    // 结束游戏
    public void finishGame() {
        finishGame = true;
    }

    // 重新开始游戏
    public void startGame() {
        finishGame = false;
        gameOver = false;
        ballX = random.nextInt(200) + 20;
        ballY = random.nextInt(20) + 20;
        racketX = random.nextInt(screenWidth) + 50;
        racketY = screenHeight - 180;
    }
}
