package com.seaside.di.modules;

import com.seaside.BuildConfig;
import com.seaside.model.api.ApiService;
import com.seaside.model.api.StringConverterFactory;
import com.seaside.model.util.EntityUtils;
import com.seaside.utils.DUtils;
import dagger.Module;
import dagger.Provides;
import dagger.Reusable;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.concurrent.TimeUnit;

/**
 * Createed by Deven
 * on 2018/11/29.
 * Descibe: TODO:
 */
@Module
public class NetModule {

//    @Provides
//    @Singleton
//    Cache provideOkHttpCache(Application application) {
//        int cacheSize = 10 * 1024 * 1024; // 10 MiB
//        return new Cache(application.getCacheDir(), cacheSize);
//    }
/*
    String mBaseUrl;

    // 构造module所需要的参数。根据每个人的情况而定
    public NetModule(String baseUrl) {
        this.mBaseUrl = baseUrl;
    }*/
    @Provides
    @Reusable
    HttpLoggingInterceptor provideOkHttpHttpLoggingInterceptor() {
        return new HttpLoggingInterceptor(message -> {
            try {
                DUtils.eLog("Http_Response ===>>>" + URLDecoder.decode(message, "utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                DUtils.eLog("Http_Response ===>>>" + message);
            }
        });
    }

    @Provides
    @Reusable
    public OkHttpClient provideOkHttpClient(HttpLoggingInterceptor loggingInterceptor) {
        loggingInterceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        return new OkHttpClient.Builder()
//                .cache(cache)
//                .sslSocketFactory(createSSLSocketFactory())
//                .sslSocketFactory(HttpsUtils.createSSLSocketFactory(), new HttpsUtils.TrustAllCerts())
//                .hostnameVerifier(new HttpsUtils.TrustAllHostnameVerifier())
                .connectTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(new CustomInterceptor())
                .addInterceptor(loggingInterceptor)
                .build();
    }

    public class CustomInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();
            DUtils.iLog("Http_Log ===>>> body:" + original.body());
            DUtils.iLog("Http_Log ===>>> url:" + original.url());
            Request request = original.newBuilder()
                    .header("User-Agent", "android")
                    .header("Accept", "application/vnd.yourapi.v1.full+json")
                    .method(original.method(), original.body())
                    .build();
            DUtils.iLog("Http_Log ===>>> request_body:" + request.body());
            DUtils.iLog("Http_Log ===>>> request_toString:" + request.toString());
            return chain.proceed(request);
        }
    }

    @Provides
    @Reusable
    public Retrofit provideRetrofit(OkHttpClient okhttpClient,String mBaseUrl) {
        return new Retrofit.Builder()
                .client(okhttpClient)
//                .baseUrl(UrlUtil.HTPPS_URL)
                .baseUrl(mBaseUrl)
                .addConverterFactory(StringConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(EntityUtils.gson))//
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @Reusable
    public ApiService provideApiService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }
}
