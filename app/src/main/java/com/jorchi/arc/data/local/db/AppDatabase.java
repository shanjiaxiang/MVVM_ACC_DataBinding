package com.jorchi.arc.data.local.db;

import android.content.Context;
import android.util.Log;

import com.jorchi.arc.data.local.db.dao.GirlDao;
import com.jorchi.arc.data.local.db.dao.ZhihuStoryDao;
import com.jorchi.arc.data.local.db.entity.Girl;
import com.jorchi.arc.data.local.db.entity.ZhihuStory;
import com.jorchi.arc.global.AppExecutors;
import com.jorchi.arc.global.BasicApp;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Girl.class, ZhihuStory.class}, version = 2, exportSchema = false)
//@TypeConverters(Converters.class)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DB_NAME = "test_db";
    private static AppDatabase mInstance;
    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();


    public abstract GirlDao getGirlDao();

    public abstract ZhihuStoryDao getStoryDao();

    public static AppDatabase getInstance() {
        if (mInstance == null) {
            synchronized (AppDatabase.class) {
                if (mInstance == null) {
                    mInstance = buildDatabase(BasicApp.getInstance());
                    mInstance.updateDatabaseCreated();
                }
            }
        }
        return mInstance;
    }

    public static AppDatabase buildDatabase(final Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, DB_NAME)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        Log.d(DB_NAME, "database created!");
                        getInstance().setDatabaseCreated();
                    }
                })
                .addMigrations(MIGRATION_1_2)
                .build();
    }

    public void setDatabaseCreated(){
        mIsDatabaseCreated.postValue(true);
    }

    public LiveData<Boolean> getDatabaseCreated(){
        return mIsDatabaseCreated;
    }

    private void updateDatabaseCreated(){
        if (BasicApp.getInstance().getDatabasePath(DB_NAME).exists()){
            setDatabaseCreated();
        }
    }

    /*
    * 用于数据库升级
    */
    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
//            database.execSQL("ALTER TABLE girls ADD COLUMN extra INTEGER NOT NULL DEFAULT 0");
//            database.execSQL("CREATE VIRTUAL TABLE IF NOT EXISTS `productsFts` " +
//                    "USING FTS4(`name` TEXT, `description` TEXT, content= `products`)");
//            database.execSQL("INSERT INTO productsFts (`rowid`, `name`, `description`) " +
//                    "SELECT `id`, `name`, `description` FROM products");
        }
    };

}
