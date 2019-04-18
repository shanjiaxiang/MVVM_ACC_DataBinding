package com.jorchi.arc.data.local.db;

import android.content.Context;
import android.util.Log;
import com.jorchi.arc.data.local.db.entity.Girl;
import com.jorchi.arc.global.AppExecutors;
import com.jorchi.arc.global.BasicApp;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class AppDatabaseManager {
    private static AppDatabaseManager mInstance;
    private static AppDatabase mDatabase;
    private static AppExecutors executors;
    private final MutableLiveData<Boolean> mIsLoadingGirlList = new MutableLiveData<>();



    public static AppDatabaseManager getInstance(){
        if (mInstance==null){
            synchronized (AppDatabaseManager.class){
                if (mInstance == null){
                    mInstance = new AppDatabaseManager();
                    mDatabase = AppDatabase.getInstance();
                    executors = BasicApp.getAppExecutors();
                }
            }
        }
        return mInstance;
    }

    public void insertGirlList(List<Girl> girls){

        executors.getDiskIO().execute(()->{
            Log.d("shan", "executor:"+Thread.currentThread().getName());
            mDatabase.runInTransaction(()->{
                Log.d("shan", "transaction:" +Thread.currentThread().getName());
                mDatabase.getGirlDao().insertAllGirls(girls);
            });
        });
    }

    public LiveData<List<Girl>> getAllGirls(){
        mIsLoadingGirlList.setValue(true);
        LiveData<List<Girl>> datas = mDatabase.getGirlDao().getAllGirls();
        mIsLoadingGirlList.setValue(false);
        return datas;
    }

    public void deleteGirlById(String id){
        executors.getDiskIO().execute(()->{
            Log.d("shan", "delete:"+Thread.currentThread().getName());
            mDatabase.getGirlDao().deleteById(id);
        });
    }

    public LiveData<Boolean> isLoadingGirlList(){
        return mIsLoadingGirlList;
    }


}
