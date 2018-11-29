package com.seaside.di.components;

import com.seaside.di.modules.NetModule;
import com.seaside.model.api.ApiService;
import dagger.Component;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

import javax.inject.Singleton;

/**
 * Createed by Deven
 * on 2018/11/29.
 * Descibe: TODO:
 */
@Component(modules = NetModule.class)
@Singleton
public interface NetComponent {
    ApiService getApiService();
    OkHttpClient getOkHttp();
    Retrofit getRetrofit();
}
