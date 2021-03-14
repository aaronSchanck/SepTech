package com.septech.centauri.database;

import com.septech.centauri.database.syzygy.SyzygyDatabase;
import com.septech.centauri.database.syzygy.models.User;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Database {
    private final String TAG = "centauri-Database";

    private SyzygyDatabase syzygy;

    public Database() {
        initLocalDB();
    }

    private void initLocalDB() {
        //TODO initialize local sqlite database
    }


}
