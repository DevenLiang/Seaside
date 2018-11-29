package com.seaside.utils;

import android.content.Context;
import android.support.annotation.StringRes;
import android.util.Log;
import com.seaside.SeasideApplication;

/**
 * Createed by Deven
 * on 2018/11/29.
 * Descibe: TODO:
 */
public class DUtils {
    private static boolean isLog = false;
    public static Context context;

    /**
     * 初始化,绑定上下文
     * @param application applicationContext
     */
    public static void init(Context application) {
        context = application;
    }

    /**
     * 删除绑定
     */
    public static void remove() {
        context = null;
    }

    public static void iLog(String content) {
        Log.i("App_Log", content);
    }

    public static void eLog(String content) {
        Log.e("App_Log", content);
    }

    public static void dLog(String content) {
        Log.d("App_Log", content);
    }

    public static void wLog(String content) {
        if (isLog) return;
        Log.w("App_Log", content);
    }

    public static void logI(String content) {
        if (isLog) return;
        Log.i("test", "result=== " + content);
    }


    public static void toastShow(CharSequence content) {
        toastShowCenter(content);
//        ToastUtils.toastShow(content);
    }

    public static void toastShow(@StringRes int resId) {
        toastShowCenter(resId);
//        ToastUtils.toastShow(resId);
    }

    public static void toastShowCenter(CharSequence content) {
        try {
            ToastUtil.showToast(SeasideApplication.getInstance(), content.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void toastShowCenter(int resId) {
        try {
            ToastUtil.showToast(SeasideApplication.getInstance(), resId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
