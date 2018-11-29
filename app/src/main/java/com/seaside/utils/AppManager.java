package com.seaside.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.Stack;

/**
 * Createed by Deven
 * on 2018/11/29.
 * Descibe: activity管理器
 */
public class AppManager {
    private static Stack<Activity> mActivityStack;
    private static AppManager mAppManager;

    private AppManager() {
    }

    /**
     * 单一实例
     */
    public static AppManager getInstance() {
        if (mAppManager == null) {
            mAppManager = new AppManager();
        }
        return mAppManager;
    }

    /**
     * 添加Activity到堆栈
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        if (mActivityStack == null) {
            mActivityStack = new Stack<Activity>();
        }
        mActivityStack.add(activity);
    }

    /**
     * 获取栈顶Activity（堆栈中最后一个压入的）
     */
    public Activity getTopActivity() {
        Activity activity = mActivityStack.lastElement();
        return activity;
    }

    /**
     * 结束栈顶Activity（堆栈中最后一个压入的）
     */
    public void killTopActivity() {
        Activity activity = mActivityStack.lastElement();
        killActivity(activity);
    }

    /**
     * 结束指定的Activity
     *
     * @param activity
     */
    private void killActivity(Activity activity) {
        try {
            if (activity != null) {
                mActivityStack.remove(activity);
                activity.finish();
            }
        } catch (Exception e) {

        }
    }

    /**
     * 结束指定类名的Activity
     *
     * @param cls
     */
    public void killActivity(Class<?> cls) {
        for (Activity activity : mActivityStack) {
            if (activity.getClass().equals(cls)) {
                killActivity(activity);
            }
        }
    }

    /**
     * 得到指定类名的Activity
     *
     * @param cls
     * @return
     */
    public Activity getActivity(Class<?> cls) {
        Activity activity = null;
        for (Activity activitys : mActivityStack) {
            if (activitys.getClass().equals(cls)) {
                activity = activitys;
            }
        }
        return activity;
    }

    /**
     * 看当前最后一个压入的Actvity是否是指定类名的atvity
     *
     * @param cls
     * @return
     */
    public boolean isTopActivity(Class<?> cls) {
        if (mActivityStack.lastElement().getClass().equals(cls)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 结束所有Activity(0包含首页;1反之)
     */
    public void killAllActivity() {
        if (mActivityStack != null && mActivityStack.size() > 0) {
            for (int i = 0, size = mActivityStack.size(); i < size; i++) {
                if (null != mActivityStack.get(i)) {
                    mActivityStack.get(i).finish();
                }
            }
            mActivityStack.clear();
        }

    }

    /**
     * 退出应用程序
     *
     * @param context
     */
    public void AppExit(Context context) {
        try {
            //killAllActivity();
            ActivityManager activityMgr = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.restartPackage(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {

        }
    }
}
