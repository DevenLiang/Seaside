package com.seaside.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.seaside.R;
import com.seaside.SeasideApplication;

/**
 * Createed by Deven
 * on 2018/11/29.
 * Descibe: TODO:
 */
public class ToastUtil {

    private static Toast toast;
    private static Toast toast_red;//红色toast显示在顶部

    /**
     * 都是吐司的综合方法1
     * @param context
     * @param content
     */
    public static void showToast(Context context, String content) {
        if (SeasideApplication.getInstance().getString(R.string.network_error).equals(content)) {
            showToastRed(content);
            return;
        }
        if (toast == null) {
            toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
            LayoutInflater inflater = LayoutInflater.from(SeasideApplication.getInstance());//调用Activity的getLayoutInflater()
            View view = inflater.inflate(R.layout.toast_style, null); //加載layout下的布局
            toast.setView(view);
        }
        View view = toast.getView();
        if (view != null) {
            TextView textView = view.findViewById(R.id.tv_toast);
            textView.setText(content);
        }
        toast.setGravity(Gravity.CENTER, 0,0);
        toast.show();
    }
    public static void showToast(Context context, int rid) {
        if (R.string.network_error == rid) {
            showToastRed(SeasideApplication.getInstance().getString(rid));
            return;
        }
        if (toast == null) {
            toast = Toast.makeText(context, rid, Toast.LENGTH_SHORT);
            LayoutInflater inflater = LayoutInflater.from(SeasideApplication.getInstance());//调用Activity的getLayoutInflater()
            View view = inflater.inflate(R.layout.toast_style, null); //加載layout下的布局
            toast.setView(view);
        }
        View view = toast.getView();
        if (view != null) {
            TextView textView = view.findViewById(R.id.tv_toast);
            textView.setText(rid);
        }
        toast.setGravity(Gravity.CENTER, 0,0);
        toast.show();
    }

    /**
     * 红色toast显示在顶部
     * @param content toast内容
     */
    public static void showToastRed(String content) {
        if (toast_red == null) {
            toast_red = Toast.makeText(SeasideApplication.getInstance(), content, Toast.LENGTH_SHORT);
            LayoutInflater inflater = LayoutInflater.from(SeasideApplication.getInstance());//调用Activity的getLayoutInflater()
            View view = inflater.inflate(R.layout.toast_style_red, null); //加載layout下的布局
            DisplayMetrics dm = SeasideApplication.getInstance().getResources().getDisplayMetrics();
            int width = dm.widthPixels;
            LinearLayout toast_group = view.findViewById(R.id.ll_toast_group);
            LinearLayout.LayoutParams llp = (LinearLayout.LayoutParams) toast_group.getLayoutParams();
            llp.width = width;
            toast_group.setLayoutParams(llp);
            toast_red.setView(view);
        }
        View view = toast_red.getView();
        if (view != null) {
            TextView textView = view.findViewById(R.id.tv_toast);
            textView.setText(content);
        }
        toast_red.setGravity(Gravity.TOP, 0,0);
        toast_red.show();
    }

    /**
     *
     * @param context
     * @param content toast内容
     * @param location
     */
    public static void showToast(Context context, String content,String location) {
        if (toast == null) {
            toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
            LayoutInflater inflater = LayoutInflater.from(SeasideApplication.getInstance());//调用Activity的getLayoutInflater()
            View view = inflater.inflate(R.layout.toast_style, null); //加載layout下的布局
            toast.setView(view);
        }
        View view = toast.getView();
        if (view != null) {
            TextView textView = view.findViewById(R.id.tv_toast);
            textView.setText(content);
        }
        if (location.equals("bottom")) {
            toast.setGravity(Gravity.BOTTOM, 0, DensityUtil.dip2px(100));
        }
        toast.show();
    }

}
