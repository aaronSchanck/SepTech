package com.septech.centauri.model.executor;

import androidx.annotation.NonNull;

import com.septech.centauri.viewmodel.executor.ThreadExecutor;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

@Singleton
public class JobExecutor implements Executor {
    private final ExecutorService executor;

    JobExecutor() {
        this.executor = Executors.newFixedThreadPool(10);
    }

    public void submit(@NonNull Callable callable) {
        this.executor.submit(callable);
    }

    @Override
    public void execute(Runnable command) {

    }
}
