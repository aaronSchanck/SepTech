package com.septech.centauri.data.cache.executors;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DiskIO {

    private static Executor executor;

    private DiskIO() {
    }

    public static Executor getInstance() {
        if (executor == null) {
            executor = Executors.newSingleThreadExecutor();
        }

        return executor;
    }
}
