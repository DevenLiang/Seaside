package com.seaside.di.modules;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.seaside.SeasideApplication;
import dagger.Module;
import dagger.Provides;
import dagger.Reusable;

/**
 * Createed by Deven
 * on 2018/12/7.
 * Descibe: TODO:
 */
@Module
public class AppModule {
/*
    SeasideApplication seasideApplication;

    public AppModule(SeasideApplication seasideApplication) {
        this.seasideApplication = seasideApplication;
    }

    @Provides
    @Singleton
    Application providesApplication() {
        return seasideApplication;
    }*/

    @Provides
    @Reusable
    SharedPreferences providesSharedPreferences(SeasideApplication application) {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

}
