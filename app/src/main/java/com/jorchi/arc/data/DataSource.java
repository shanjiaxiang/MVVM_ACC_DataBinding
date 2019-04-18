package com.jorchi.arc.data;

import com.jorchi.arc.data.local.db.entity.Girl;

import java.util.List;

import androidx.lifecycle.LiveData;

public interface DataSource {
    LiveData<List<Girl>> getGirlList(int index);

    LiveData<Boolean> isLoadingGirlList();
}
