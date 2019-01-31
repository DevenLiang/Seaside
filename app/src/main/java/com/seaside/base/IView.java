package com.seaside.base;

import com.seaside.comm.ApiException;
import com.seaside.comm.dialog.LoadingDialog;
import com.trello.rxlifecycle2.LifecycleTransformer;

/**
 * Createed by Deven
 * on 2018/12/14.
 * Descibe: TODO:
 */
public interface IView {
    /**
     * 显示加载
     */
    void showLoading();

    void showLoading(@LoadingDialog.MODE int mode);

    /**
     * 隐藏加载
     */
    void hideLoading();

    /**
     * 显示信息
     */
    void showMessage(String message);

    <T> LifecycleTransformer<T> bindToLifecycle();

    void handleError(ApiException e);
}
