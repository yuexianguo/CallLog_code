package com.phone.base.common.network;

import android.util.Log;

import androidx.annotation.NonNull;

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory;
import com.phone.base.common.network.api.CoroutineController;
import com.phone.base.common.network.api.StrutController;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiFactory {

    public static final String BASE_DEFAULT_IP = "10.10.10.1";
    private volatile static StrutController strutController;
    private volatile static StrutController strutControllerForOTA;
    private volatile static CoroutineController coroutineController;
    private volatile static String targetBaseIp = BASE_DEFAULT_IP;

    public synchronized static StrutController getStrutController() {
        if (strutController == null) {
            strutController = create("http://" + targetBaseIp + "/", StrutController.class);
        }
        return strutController;
    }

    public synchronized static CoroutineController getCoroutineController() {
        if (coroutineController == null) {
            coroutineController = createForCoroutine("http://" + targetBaseIp + "/", CoroutineController.class);
        }
        return coroutineController;
    }

    public synchronized static StrutController getControllerLongTimeOut() {
        if (strutControllerForOTA == null) {
            strutControllerForOTA = createForOTA("http://" + targetBaseIp + "/", StrutController.class);
            Log.d("ApiFactory", "getControllerLongTimeOut: targetBaseIp=" + targetBaseIp);
        }
        return strutControllerForOTA;
    }

    public synchronized static void setTargetBaseIp(@NonNull String ip) {
        if (!targetBaseIp.equals(ip)) {
            strutController = null;
            strutControllerForOTA = null;
            coroutineController = null;
            targetBaseIp = ip;
        }
    }

    public synchronized static String getTargetBaseIp(){
        return targetBaseIp;
    }

    private static <T> T createForCoroutine(String baseUrl, Class<T> service) {
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(httpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory.create());

        return retrofitBuilder.build().create(service);
    }

    private static <T> T create(String baseUrl, Class<T> service) {
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(httpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());

        return retrofitBuilder.build().create(service);
    }

    private static <T> T createForOTA(String baseUrl, Class<T> service) {
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(httpClientForOTA())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());

        return retrofitBuilder.build().create(service);
    }

    private static OkHttpClient httpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS);
//        if (BuildConfig.DEBUG) {
            builder.addInterceptor(new LogInfoInterceptor());
//        }
        return builder.build();
    }

    private static OkHttpClient httpClientForOTA() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(60 * 5, TimeUnit.SECONDS)
                .readTimeout(60 * 5, TimeUnit.SECONDS)
                .writeTimeout(60 * 5, TimeUnit.SECONDS);
//        if (BuildConfig.DEBUG) {
//            builder.addInterceptor(new LogInfoInterceptor());
//        }
        return builder.build();
    }

}
