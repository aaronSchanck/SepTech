package com.septech.centauri.database;

import com.septech.centauri.models.User;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Database {
    private final String TAG = "centauri-Database";

    private static final ExecutorService executor = Executors.newFixedThreadPool(3);

    private static com.septech.centauri.database.API.Users.Get usersGet = new com.septech.centauri.database.API.Users.Get();
    private static com.septech.centauri.database.API.Users.Post usersPost = new com.septech.centauri.database.API.Users.Post();
    private static com.septech.centauri.database.API.Users.Delete usersDelete = new com.septech.centauri.database.API.Users.Delete();
    private static com.septech.centauri.database.API.Users.Put usersPut = new com.septech.centauri.database.API.Users.Put();

    private com.septech.centauri.database.API.Items.Get itemsGet = new com.septech.centauri.database.API.Items.Get();;
//    private com.septech.centauri.database.API.Items.Post itemsPost;
//    private com.septech.centauri.database.API.Items.Delete itemsDelete;
//    private com.septech.centauri.database.API.Items.Put itemsPut;

    public static User login(String username, String password) {
        Callable<User> callable = () -> usersPost.login(username, password);
        Future<User> result = executor.submit(callable);

        try {
            return result.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static User getSingleUser(int i) {
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
