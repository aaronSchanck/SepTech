package com.septech.centauri.database.syzygy;

import com.septech.centauri.database.syzygy.models.User;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SyzygyDatabase {
    private final String TAG = "centauri-syzygy";

    private static final ExecutorService executor = Executors.newFixedThreadPool(3);

    private com.septech.centauri.database.syzygy.api.Users.Get usersGet;
    private com.septech.centauri.database.syzygy.api.Users.Post usersPost;
    private com.septech.centauri.database.syzygy.api.Users.Delete usersDelete;
    private com.septech.centauri.database.syzygy.api.Users.Put usersPut;

//    private com.septech.centauri.database.syzygy.api.Items.Get itemsGet;
//    private com.septech.centauri.database.syzygy.api.Items.Post itemsPost;
//    private com.septech.centauri.database.syzygy.api.Items.Delete itemsDelete;
//    private com.septech.centauri.database.syzygy.api.Items.Put itemsPut;

    public SyzygyDatabase() {
        initUserTableAPIEndpoints();
//        initItemTableAPIEndpoints();
    }

    private void initUserTableAPIEndpoints() {
        usersGet = new com.septech.centauri.database.syzygy.api.Users.Get();
        usersPost = new com.septech.centauri.database.syzygy.api.Users.Post();
        usersDelete = new com.septech.centauri.database.syzygy.api.Users.Delete();
        usersPut = new com.septech.centauri.database.syzygy.api.Users.Put();
    }

//    private void initItemTableAPIEndpoints() {
//        itemsGet = new com.septech.centauri.database.syzygy.api.Items.Get();
//        itemsPost = new com.septech.centauri.database.syzygy.api.Items.Post();
//        itemsDelete = new com.septech.centauri.database.syzygy.api.Items.Delete();
//        itemsPut = new com.septech.centauri.database.syzygy.api.Items.Put();
//    }

    public User login(String username, String password) {
        Callable<User> callable = () -> usersPost.login(username, password);
        Future<User> result = executor.submit(callable);

        try {
            return result.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public User getSingleUser(int i) {
        Callable<User> callable = () -> usersGet.getSingleUser(i);
        Future<User> result = executor.submit(callable);

        try {
            return result.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
