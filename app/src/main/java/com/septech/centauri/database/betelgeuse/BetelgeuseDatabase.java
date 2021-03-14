package com.septech.centauri.database.betelgeuse;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.septech.centauri.database.betelgeuse.dao.UserDao;
import com.septech.centauri.database.betelgeuse.models.User;

@Database(entities = {User.class}, version = BetelgeuseDatabase.DATABASE_VERSION)
public abstract class BetelgeuseDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "betelgeuse";
    public static final int DATABASE_VERSION = 1;

    public abstract UserDao userDao();
}
