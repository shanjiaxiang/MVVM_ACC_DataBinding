package com.jorchi.arc.viewmodel;

import android.app.Application;

import com.jorchi.arc.data.DataRepository;
import com.jorchi.arc.data.local.db.entity.Girl;
import com.jorchi.arc.global.BasicApp;
import com.jorchi.arc.utils.Util;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class GirlListViewModel extends AndroidViewModel {
    private final MutableLiveData<Integer> mGirlPageIndex = new MutableLiveData<>();
    private final LiveData<List<Girl>> mGirls;
    private DataRepository mRepository;

    public GirlListViewModel(@NonNull Application application, DataRepository repository) {
        super(application);
        this.mRepository = repository;
        mGirls = Transformations.switchMap(mGirlPageIndex, new Function<Integer, LiveData<List<Girl>>>() {
            @Override
            public LiveData<List<Girl>> apply(Integer input) {
                return mRepository.getGirlList(input);
            }
        });
    }

    public LiveData<List<Girl>> getGirlsLiveData() {
        return mGirls;
    }

    public LiveData<Boolean> getLoadMoreState() {
        return mRepository.isLoadingGirlList();
    }

    public void refreshGirlsData() {
        mGirlPageIndex.setValue(1);
    }

    public void loadNextPageGirls() {
        if (!Util.isNetworkConnected(BasicApp.getInstance())) {
            return;
        }
        mGirlPageIndex.setValue(mGirlPageIndex.getValue() != null ? 1 : mGirlPageIndex.getValue() + 1);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        private final Application mApplication;
        private final DataRepository mFactoryDataRepository;

        public Factory(Application application, DataRepository factoryDataRepository) {
            this.mApplication = application;
            this.mFactoryDataRepository = factoryDataRepository;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new GirlListViewModel(mApplication, mFactoryDataRepository);
        }
    }

}
