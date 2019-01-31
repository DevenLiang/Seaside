package com.seaside.base;

import android.app.Activity;

/**
 * Createed by Deven
 * on 2018/12/14.
 * Descibe: TODO:
 */
public interface IPresenter<T extends IView> {

    /**
     * 做一些初始化操作
     */
    void onStart();

    /**
     * 在框架中 {@link Activity#onDestroy()} 时会默认调用 {@link IPresenter#onDestroy()}
     */
    void onDestroy();

    void attachView(T view);

    T getView();

}
