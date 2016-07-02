package com.draw;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.ziyuntest.R;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Ziyun on 2016/7/2.
 */
public class PinBallActivity extends AppCompatActivity {
	private int screenWidth; // 屏幕宽
	private int screenHeight; // 屏幕高
	private int racketX; // 球拍水平位置
	private int racketY; // 球拍垂直位置
	private final int RACKET_WIDTH = 70; // 球拍宽度
	private final int RACKET_HEIGHT = 20; // 球拍高度
	private int ballX; // 小球水平位置
	private int ballY; // 小球垂直位置
	private final int BALL_SIZE = 12; // 小球大小
	private boolean gameOver = false; // 记录游戏是否结束
	private static final String TAG = "Ziyun"; // 日志标志
	private int speedX; // 小球水平速度
	private int speedY; // 小球垂直速度；
	private double speedRate; // 速率
	private Random random; // 随机数生成器
	private static MarbleGameView gameView;
    private static final int MSG_CODE = 0x123;
    Timer timer;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);  // 去掉窗口标题
        // 全屏显示
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // 获取屏幕宽高
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;

        // 创建布局
        gameView = MarbleGameView.newInstance(this, screenWidth, screenHeight);
        setContentView(gameView);

        ballX = gameView.getBallX();
        ballY = gameView.getBallY();
        racketX = gameView.getRacketX();
        racketY = gameView.getRacketY();
        random = new Random();
        speedRate = random.nextDouble() - 0.5;
        speedY = 10;
        speedX = (int)(speedY * speedRate * 2);

//        final Handler handler = new Handler(){
//            @Override
//            public void handleMessage(Message msg) {
//                if (msg.what == MSG_CODE){
//                    gameView.invalidate();
//                }
//            }
//        };

        gameView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                switch (event.getKeyCode()){
                    // 控制球拍左移
                    case KeyEvent.KEYCODE_A:
                        if(racketX > 0){
                            racketX -= 10;
                        }
                        break;

                    // 控制球拍右移
                    case KeyEvent.KEYCODE_D:
                        if (racketX < screenWidth - RACKET_WIDTH){
                            racketX += 10;
                        }
                        break;
                }
                gameView.setRacketX(racketX);
                gameView.invalidate();
                return true;
            }
        });

        setTimer();
    }

    private void setTimer() {
        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == MSG_CODE){
                    gameView.invalidate();
                }
            }
        };

        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                ballX = gameView.getBallX();
                ballY = gameView.getBallY();
                racketX = gameView.getRacketX();
                racketY = gameView.getRacketY();
                // 如果小球碰到左右边框
                if (ballX <= 0 || ballX >= screenWidth - BALL_SIZE){
                    speedX = -speedX;
                }
                // 如果小球的高度超出球拍高度且不在小球的范围内，则宣告游戏结束
                if (ballY >= (racketY - BALL_SIZE) && (ballX < racketX || ballX > racketX + RACKET_WIDTH)){
                    gameOver = true;
                    gameView.setGameOver(gameOver);
                }
                // 如果球在球拍范围内，则反弹
                else if (ballY < 0 || (ballY >= (racketY - BALL_SIZE) && (ballX >= racketX && racketX <= racketX + RACKET_WIDTH))){
                    speedY = -speedY;
                }
                // 变化小球位置
                ballX += speedX;
                ballY += speedY;
                gameView.setBallX(ballX);
                gameView.setBallY(ballY);

                // 发送消息，通知系统重绘
                handler.sendEmptyMessage(MSG_CODE);
            }
        }, 0, 100);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.menu_pinball, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            // 主动结束游戏
            case R.id.menu_pinball_over:
                gameView.finishGame();
                gameView.invalidate();
                break;
            // 重新开始游戏
            case R.id.menu_pinball_again:
                gameView.startGame();
                gameView.invalidate();
                break;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();     //释放定时器
    }
}
