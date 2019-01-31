package com.seaside;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.im.android.api.JMessageClient;
import com.seaside.di.components.AppComponent;
import com.seaside.di.components.DaggerAppComponent;
import com.seaside.model.util.UrlUtil;
import com.seaside.utils.AppManager;
import com.seaside.utils.CrashHandler;
import com.seaside.utils.DUtils;
import com.seaside.utils.DensityUtil;

/**
 * Createed by Deven
 * on 2018/11/29.
 * Descibe: TODO:
 */
public class SeasideApplication extends Application {
    private static SeasideApplication instance;

    public static SeasideApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //异常捕获
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);
        //工具类
        DUtils.init(this);
        //转换类
        DensityUtil.init(this);
        initNet();

        JMessageClient.setDebugMode(true);
        JMessageClient.init(this);

        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        JPushInterface.setAlias(this, 1, "DevenL");
    }

    @Override
    public void onTerminate() {
        DUtils.remove();
        DensityUtil.remove();
        AppManager.getInstance().AppExit(instance);
        instance = null;
        // 程序终止的时候执行
        super.onTerminate();
    }

    private AppComponent mAppComponent;

    private void initNet() {
        mAppComponent = DaggerAppComponent.builder()
                .application(this)
                .baseUrl(UrlUtil.HTPPS_URL)
                .build();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
