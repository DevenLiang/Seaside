package com.seaside;

import android.app.Application;
import cn.jpush.android.api.JPushInterface;
import com.seaside.di.components.DaggerNetComponent;
import com.seaside.di.components.NetComponent;
import com.seaside.di.modules.NetModule;
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

    private NetComponent netComponent;

    private void initNet() {
        netComponent = DaggerNetComponent.builder()
                .netModule(new NetModule())
                .build();
    }

    public NetComponent getNetComponent() {
        return netComponent;
    }
}
