package com.jorchi.arc.global;

import android.app.Application;
import android.content.Context;

import com.jorchi.arc.data.local.db.AppDatabase;


public class BasicApp extends Application {
    private static AppExecutors APP_EXECUTORS;
    public static BasicApp INSTANCE;

    public static BasicApp getInstance(){
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        APP_EXECUTORS = new AppExecutors();
    }

    public static AppExecutors getAppExecutors(){
        return APP_EXECUTORS;
    }
}
