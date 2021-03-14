package com.septech.centauri.utils;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.room.Room;

import com.septech.centauri.data.cache.database.betelgeuse.BetelgeuseDatabase;
import com.septech.centauri.database.syzygy.SyzygyDatabase;

public class Singleton {
    private static Singleton instance;

    private final String PREFS_NAME = "com.septech.centauri.UserPrefs";

    private SharedPreferences settings;

    private SyzygyDatabase syzygy;

    private BetelgeuseDatabase betelgeuse;

    private Singleton() {

    }

    public static synchronized Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }

    public SharedPreferences getUserPrefs(Context ctx) {
        if (settings == null) {
            settings = ctx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        }
        return settings;
    }

    public SyzygyDatabase getSyzygy() {
        if (syzygy == null) {
            syzygy = new SyzygyDatabase();
        }

        return syzygy;
    }

    public BetelgeuseDatabase getBetelgeuse(Context ctx) {
        if (betelgeuse == null) {
            betelgeuse = Room.databaseBuilder(ctx,
                    BetelgeuseDatabase.class, BetelgeuseDatabase.DATABASE_NAME).build();
        }
        return betelgeuse;
    }
}
