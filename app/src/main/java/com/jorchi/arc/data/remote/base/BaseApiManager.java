package com.jorchi.arc.data.remote.base;

import android.util.Log;

import com.jorchi.arc.BuildConfig;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public abstract class BaseApiManager {

    private static final int DEFAULT_TIMEOUT = 20;
    private static String TAG = "retrofit";
    protected Retrofit mRetrofit;

    protected abstract Response publicIntercept(Interceptor.Chain chain) throws IOException;

    public BaseApiManager(String baseUrl) {
        this.mRetrofit = new Retrofit.Builder()
                .client(this.getClient())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();
    }

    private OkHttpClient getClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(new HttpLoggingInterceptor(this.logger()).setLevel(HttpLoggingInterceptor.Level.BODY));
        }
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                return BaseApiManager.this.publicIntercept(chain);
            }
        });
        return builder.build();
    }

    private HttpLoggingInterceptor.Logger logger() {
        return new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d(TAG, message);
            }
        };
    }

    protected Observable toSubscribe(Observable observable, String tag) {
        this.TAG = tag;
        return observable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
