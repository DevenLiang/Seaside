package com.seaside.di.components;

import android.content.SharedPreferences;
import com.seaside.SeasideApplication;
import com.seaside.di.modules.AppModule;
import com.seaside.di.modules.NetModule;
import com.seaside.model.api.ApiService;
import dagger.BindsInstance;
import dagger.Component;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Createed by Deven
 * on 2018/12/7.
 * Descibe: TODO:
 */
@Component(modules = {AppModule.class, NetModule.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(SeasideApplication seasideApplication);

        @BindsInstance
        Builder baseUrl(String baseUrl);

        AppComponent build();

    }

    ApiService getApiService();
    OkHttpClient getOkHttp();
    Retrofit getRetrofit();
    SharedPreferences sharedPreferences();
    SeasideApplication application();
    void inject(SeasideApplication seasideApplication);
}
