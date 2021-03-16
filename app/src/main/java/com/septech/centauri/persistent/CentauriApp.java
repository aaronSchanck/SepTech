package com.septech.centauri.persistent;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

public class CentauriApp extends Application {
    private static final String TAG = CentauriApp.class.getSimpleName();

    private static CentauriApp mInstance;

    @Contract(pure = true)
    @Nullable
    public static Context getAppContext() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG, "onCreate() called");
        mInstance = this;
    }
}
