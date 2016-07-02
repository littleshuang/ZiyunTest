package com.utils;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by Ziyun on 2016/7/1.
 *
 * dp、sp、px间相互转换的公式
 */
public class DisplayUtil {

	/**
	 * 将px值转换为dp/dip值，保证尺寸大小不变
	 * 
	 * @param context
	 * @param pxValue
	 * @return
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f); // 向上取整
	}

	/**
	 * 将dp/dip值转换为px值，保证尺寸大小不变
	 * 
	 * @param context
	 * @param dipValue
	 * @return
	 */
	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

    /**
     * 将dp/dip值转换为px值，保证尺寸大小不变,使用系统提供的转换类TypedValue
     * @param context
     * @param dip
     * @return
     */
	public static int dip2px(Context context, int dip) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, context.getResources()
				.getDisplayMetrics());
	}

    /**
     * 将px值转换为sp值，保证文字大小不变
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2sp(Context context, float pxValue){
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int)(pxValue / scale + 0.5f);
    }

    /**
     * 将sp值转化为px值，保证文字大小不变
     * @param context
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, float spValue){
        final  float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int)(spValue * scale + 0.5);
    }

    /**
     * 将sp值转化为px值，保证文字大小不变，使用系统提供的转换类TypedValue
     * @param context
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, int spValue){
        return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, context.getResources()
        .getDisplayMetrics());
    }
}
