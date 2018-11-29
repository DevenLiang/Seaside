package com.seaside.di.modules;

import com.seaside.BuildConfig;
import com.seaside.model.api.ApiService;
import com.seaside.model.api.StringConverterFactory;
import com.seaside.model.util.EntityUtils;
import com.seaside.model.util.UrlUtil;
import com.seaside.utils.DUtils;
import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.inject.Singleton;
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
    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor((HttpLoggingInterceptor.Logger) message -> {
            try {
                DUtils.eLog("Http_Response ===>>>" + URLDecoder.decode(message, "utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                DUtils.eLog("Http_Response ===>>>" + message);
            }
        });
        loggingInterceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        return new OkHttpClient.Builder()
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
    @Singleton
    public Retrofit provideRetrofit(OkHttpClient okhttpClient) {
        return new Retrofit.Builder()
                .client(okhttpClient)
                .baseUrl(UrlUtil.HTPPS_URL)
                .addConverterFactory(StringConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(EntityUtils.gson))//
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    public ApiService provideApiService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }
}
