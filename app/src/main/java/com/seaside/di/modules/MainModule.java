package com.seaside.di.modules;

import com.seaside.presenter.MainPresenter;
import dagger.Module;
import dagger.Provides;

/**
 * Createed by Deven
 * on 2018/12/7.
 * Descibe: TODO:
 */
@Module
public class MainModule {
    @Provides
    MainPresenter providesMainPresenter(){
        return new MainPresenter();
    }
}
