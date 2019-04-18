package com.jorchi.arc.data.remote;

import android.content.Context;

import com.jorchi.arc.data.DataSource;
import com.jorchi.arc.data.local.db.AppDatabase;
import com.jorchi.arc.data.local.db.AppDatabaseManager;
import com.jorchi.arc.data.local.db.entity.Girl;
import com.jorchi.arc.data.remote.base.HttpOnNextListener;
import com.jorchi.arc.data.remote.base.ProgressSubscriber;
import com.jorchi.arc.data.remote.model.GirlData;
import com.jorchi.arc.global.BasicApp;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.Observer;
import retrofit2.Response;

public class RemoteDataSource implements DataSource {
    private static RemoteDataSource mInstance;
    private final MutableLiveData<Boolean> mIsLoadingGirlList;
    private final MutableLiveData<List<Girl>> mGirlList;
    private Context mContext;

    {
        mIsLoadingGirlList = new MutableLiveData<>();
        mGirlList = new MutableLiveData<>();
    }

    public RemoteDataSource() {
        this.mContext = BasicApp.getInstance();
    }

    public static RemoteDataSource getInstance() {
        if (mInstance == null) {
            synchronized (RemoteDataSource.class) {
                if (mInstance == null) {
                    mInstance = new RemoteDataSource();
                }
            }
        }
        return mInstance;
    }


    @Override
    public LiveData<List<Girl>> getGirlList(int index) {
        mIsLoadingGirlList.setValue(true);
        ApiManager.getInstance().getGirlList(index).subscribe(new ProgressSubscriber(mContext, new HttpOnNextListener<GirlData>() {
                    @Override
                    public void onNext(GirlData response) {
                        if (!response.isError()) {
                            mGirlList.setValue(response.getResults());
                            refreshLoaclGrilList(response.getResults());
                        }
                        mIsLoadingGirlList.setValue(false);
                    }

                    @Override
                    public void onError() {
                        mIsLoadingGirlList.setValue(false);
                    }
                }));
        return mGirlList;
    }

    @Override
    public LiveData<Boolean> isLoadingGirlList() {
        return mIsLoadingGirlList;
    }


    private void refreshLoaclGrilList(List<Girl> girlList) {
        if (girlList == null || girlList.isEmpty()) {
            return;
        }
        AppDatabaseManager.getInstance().insertGirlList(girlList);
    }
}
