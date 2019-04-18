package com.jorchi.arc.data.local.db.dao;

import com.jorchi.arc.data.local.db.entity.Girl;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface GirlDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllGirls(List<Girl> girls);

    @Query("SELECT * FROM girls")
    LiveData<List<Girl>> getAllGirls();

    @Query("SELECT * FROM girls WHERE _id=:id")
    LiveData<Girl> searchGirlById(String id);

    @Query("DELETE FROM girls WHERE _id=:id")
    void deleteById(String id);

}
