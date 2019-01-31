package com.seaside.utils;

import android.content.Context;
import android.support.annotation.StringRes;
import android.util.Log;
import com.seaside.SeasideApplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

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

    /**
     * 把Asset资源Copy到本地指定路径
     * @param context
     * @param assetsPath    asset路径
     * @param savePath  保存路径
     */
    public static void copyFilesFromAssets(Context context, String assetsPath, String savePath){
        try {
            String fileNames[] = context.getAssets().list(assetsPath);// 获取assets目录下的所有文件及目录名
            if (fileNames.length > 0) {// 如果是目录
                File file = new File(savePath);
                file.mkdirs();// 如果文件夹不存在，则递归
                for (String fileName : fileNames) {
                    copyFilesFromAssets(context, assetsPath + "/" + fileName,
                            savePath + "/" + fileName);
                }
            } else {// 如果是文件
                InputStream is = context.getAssets().open(assetsPath);
                FileOutputStream fos = new FileOutputStream(new File(savePath));
                byte[] buffer = new byte[1024];
                int byteCount = 0;
                while ((byteCount = is.read(buffer)) != -1) {// 循环从输入流读取
                    // buffer字节
                    fos.write(buffer, 0, byteCount);// 将读取的输入流写入到输出流
                }
                fos.flush();// 刷新缓冲区
                is.close();
                fos.close();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
