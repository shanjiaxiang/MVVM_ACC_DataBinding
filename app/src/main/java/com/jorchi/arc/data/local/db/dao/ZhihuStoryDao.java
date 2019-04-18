package com.jorchi.arc.data.local.db.dao;

import com.jorchi.arc.data.local.db.entity.ZhihuStory;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface ZhihuStoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllStorys(ZhihuStory... stories);

    @Query("SELECT * FROM zhihustorys")
    LiveData<List<ZhihuStory>> getAllStorys();

    @Query("SELECT * FROM zhihustorys WHERE id=:id")
    LiveData<ZhihuStory> getStoryById(String id);
}
