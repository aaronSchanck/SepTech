package com.septech.centauri.model.cache.database.betelgeuse;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.septech.centauri.model.cache.database.betelgeuse.dao.UserDao;
import com.septech.centauri.model.cache.database.betelgeuse.models.UserModel;
import com.septech.centauri.persistent.CentauriApp;

@Database(entities = {UserModel.class}, version = BetelgeuseDatabase.DATABASE_VERSION)
public abstract class BetelgeuseDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "betelgeuse";
    public static final int DATABASE_VERSION = 1;

    public abstract UserDao userDao();

    private static BetelgeuseDatabase instance;

    public static BetelgeuseDatabase getDatabase() {
        if (instance == null) {
            synchronized (BetelgeuseDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(CentauriApp.getAppContext(),
                            BetelgeuseDatabase.class, DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return instance;
    }
}
