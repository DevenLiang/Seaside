package com.seaside.base.impl;

import android.os.Bundle;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.seaside.base.IView;
import com.seaside.utils.ViewUtils;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import dagger.android.AndroidInjection;

/**
 * Createed by Deven
 * on 2018/12/14.
 * Descibe: TODO:
 */
public abstract class BaseActivity extends RxAppCompatActivity implements IView {
    //解除绑定控件
    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //dagger2统一注册需要的对象
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        ViewUtils.setImmersionStateMode(this);
        try {
            int layoutResID = getLayoutResId(savedInstanceState);
            if (layoutResID != 0) {
                setContentView(layoutResID);
                //和View绑定
                mUnbinder = ButterKnife.bind(this);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        initData(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY)
            mUnbinder.unbind();
        this.mUnbinder = null;
    }

    public abstract int getLayoutResId(Bundle savedInstanceState);

    public abstract void initData(Bundle savedInstanceState);

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(String message) {

    }
}
