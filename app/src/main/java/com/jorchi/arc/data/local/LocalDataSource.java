package com.jorchi.arc.data.local;

import com.jorchi.arc.data.DataSource;
import com.jorchi.arc.data.local.db.AppDatabaseManager;
import com.jorchi.arc.data.local.db.entity.Girl;

import java.util.List;

import androidx.lifecycle.LiveData;

public class LocalDataSource implements DataSource {
    private static LocalDataSource INSTANCE = null;

    public static LocalDataSource getInstance() {
        if (INSTANCE == null) {
            synchronized (LocalDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LocalDataSource();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public LiveData<List<Girl>> getGirlList(int index) {
        return AppDatabaseManager.getInstance().getAllGirls();
    }

    @Override
    public LiveData<Boolean> isLoadingGirlList() {
        return AppDatabaseManager.getInstance().isLoadingGirlList();
    }
}
