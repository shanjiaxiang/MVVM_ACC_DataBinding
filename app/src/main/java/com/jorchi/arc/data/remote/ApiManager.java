package com.jorchi.arc.data.remote;

import android.util.Log;

import com.jorchi.arc.BuildConfig;
import com.jorchi.arc.data.local.db.entity.Girl;
import com.jorchi.arc.data.remote.base.BaseApiManager;
import com.jorchi.arc.data.remote.base.ProgressSubscriber;

import java.io.IOException;
import java.util.List;

import androidx.lifecycle.LiveData;
import io.reactivex.Observable;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ApiManager extends BaseApiManager {
    private volatile static ApiManager mInstance;
    private ApiService mApiService;

    public ApiManager() {
        super(BuildConfig.Api_CN);
        mApiService = mRetrofit.create(ApiService.class);
    }

    public static ApiManager getInstance() {
        if (mInstance == null) {
            synchronized (ApiManager.class) {
                if (mInstance == null) {
                    mInstance = new ApiManager();
                }
            }
        }
        return mInstance;
    }

    @Override
    protected Response publicIntercept(Interceptor.Chain chain) throws IOException {
        long time = System.currentTimeMillis();
        Request request = chain.request().newBuilder()
//                .addHeader("uid", App.getUserId())
//                .addHeader("access_token", md5Token)
//                .addHeader("nonce_str", mTime)
//                .addHeader("cSignVersion", "1")
//                .addHeader("cTime", String.valueOf(time))
//                .addHeader("Content-Type", "application/json")
//                .addHeader("Accept", "application/json")
                .build();
        String header = "{";
        Headers headers = request.headers();
        for (int i = 0; i < headers.size(); i++) {
            header += headers.name(i) + ":" + headers.value(i);
            header += i == headers.size() - 1 ? "" : ", ";
        }
        header += "}";
        Log.d("retrofit", "HEADERS:"+  header);
        return chain.proceed(request);
    }

    public Observable getGirlList(int index) {
        return toSubscribe(mApiService.getGirlsData(index), "列表");
    }

//    public LiveData<List<Girl>> getGirlList(int index){
//
//    }
}
