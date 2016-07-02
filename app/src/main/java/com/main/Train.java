package com.main;

import android.app.Activity;
import android.content.Context;

import com.animation.AnimationActivity;
import com.animation.ValueAnimActivity;
import com.draw.HandDrawActivity;
import com.draw.PinBallActivity;
import com.draw.SimpleDrawActivity;
import com.fragment.FragmentActivity;
import com.ziyuntest.R;

/**
 * Created by Ziyun on 2016/6/30.
 */
public class Train {
	private String trainName;   // 训练部分地Activity名
	private String trainDes;    // 该训练部分的主要内容，便于后期回忆
	private Class<? extends Activity> clz;  // 训练部分Activity class，便于跳转到该Acticity

	public Train(String trainName, String trainDes, Class<? extends Activity> clz){
        this.trainName = trainName;
        this.trainDes = trainDes;
        this.clz = clz;
    }

    public Train(Context context, int nameRes, int desRes, Class<? extends Activity> clz){
        this.trainName = context.getResources().getString(nameRes);
        this.trainDes = context.getResources().getString(desRes);
        this.clz = clz;
    }

    // 构建全局训练样本集，名称和描述信息存放在资源文件中
    public static Train[] getTrains(Context context){
        Train[] trains = new Train[]{
                new Train(context, R.string.title_train_1, R.string.des_train_1, TrainActivity.class),
                new Train(context, R.string.title_train_2, R.string.des_train_2, FragmentActivity.class),
                new Train(context, R.string.title_train_3, R.string.des_train_3, MainActivity.class),
                new Train(context, R.string.title_train_4, R.string.des_train_4, AnimationActivity.class),
                new Train(context, R.string.title_train_5, R.string.des_train_5, ValueAnimActivity.class),
                new Train(context, R.string.title_train_6, R.string.des_train_6, SimpleDrawActivity.class),
                new Train(context, R.string.title_train_7, R.string.des_train_7, HandDrawActivity.class),
                new Train(context, R.string.title_train_8, R.string.des_train_8, PinBallActivity.class),

        };
        return trains;
    }

    public static String[] getTrainNames(Context context){
        int len = getTrains(context).length;
        String[] trainNames = new String[len];
        for (int i = 0; i < len; ++i){
            trainNames[i] = getTrains(context)[i].getTrainName();
        }
        return trainNames;
    }

    public static String[] getTrainDescrips(Context context){
        int len = getTrains(context).length;
        String[] trainDescrips = new String[len];
        for (int i = 0; i < len; ++i){
            trainDescrips[i] = getTrains(context)[i].getTrainDes();
        }
        return trainDescrips;
    }

    public String getTrainName() {
        return trainName;
    }

    public String getTrainDes() {
        return trainDes;
    }

    public Class<? extends Activity> getClz() {
        return clz;
    }
}
