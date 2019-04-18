package com.jorchi.arc.data;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.jorchi.arc.data.local.LocalDataSource;
import com.jorchi.arc.data.local.db.entity.Girl;
import com.jorchi.arc.data.remote.RemoteDataSource;
import com.jorchi.arc.global.BasicApp;
import com.jorchi.arc.utils.Util;

import java.util.List;

import androidx.lifecycle.LiveData;

public class DataRepository {
    private static DataRepository INSTANCE;
    private final DataSource mRemoteDataSource;
    private final DataSource mLocalDataSource;
    private static Context CONTEXT;


    public DataRepository(DataSource remoteDataSource, DataSource localDataSource) {
        this.mRemoteDataSource = remoteDataSource;
        this.mLocalDataSource = localDataSource;
    }

    public static DataRepository getInstance(){
        if (INSTANCE == null){
            synchronized (DataRepository.class){
                if (INSTANCE == null){
                    INSTANCE = new DataRepository(RemoteDataSource.getInstance(), LocalDataSource.getInstance());
                    CONTEXT = BasicApp.getInstance();
                }
            }
        }
        return INSTANCE;
    }

    public LiveData<List<Girl>> getGirlList(int index){
        if (Util.isNetworkConnected(CONTEXT)){
            Log.d("shan", "has network");
            return mRemoteDataSource.getGirlList(index);
        }else {
            Log.d("shan", "reader from database");
            return mLocalDataSource.getGirlList(index);
        }
    }

    public LiveData<Boolean> isLoadingGirlList() {
        if (Util.isNetworkConnected(BasicApp.getInstance())){
            return mRemoteDataSource.isLoadingGirlList();
        }else {
            return mLocalDataSource.isLoadingGirlList();
        }
    }
}
