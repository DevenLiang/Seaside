package com.seaside.di.components;

import com.seaside.di.modules.MainModule;
import com.seaside.ui.MainActivity;
import dagger.Component;

/**
 * Createed by Deven
 * on 2018/11/29.
 * Descibe: TODO:
 */
@Component(modules = {MainModule.class}, dependencies = AppComponent.class)
public interface MainComponent {
    void inject(MainActivity activity);
}
