package com.septech.centauri.model.repository;


import android.content.Context;

import com.septech.centauri.model.cache.database.betelgeuse.BetelgeuseDatabase;
import com.septech.centauri.model.entity.user.UserEntity;
import com.septech.centauri.model.entity.user.mapper.UserEntityDataMapper;
import com.septech.centauri.model.net.RestApi;
import com.septech.centauri.model.net.RestApiImpl;
import com.septech.centauri.persistent.CentauriApp;
import com.septech.centauri.viewmodel.models.User;
import com.septech.centauri.viewmodel.repository.UserRepository;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.inject.Inject;
import javax.inject.Singleton;

public class UserDataRepository implements UserRepository {

    private static UserDataRepository mInstance;

    private BetelgeuseDatabase database;
    private ExecutorService executor;
    private RestApi restApi;
    private UserEntityDataMapper userEntityDataMapper;

    public UserDataRepository(Context ctx) {
        this.executor = Executors.newFixedThreadPool(10);
        userEntityDataMapper = new UserEntityDataMapper();

        restApi = new RestApiImpl();
        this.database = BetelgeuseDatabase.getDatabase(ctx);
    }

    public static UserRepository getInstance() {
        if (mInstance == null) {
            mInstance = new UserDataRepository(CentauriApp.getAppContext());
        }

        return mInstance;
    }


    public User getSingleUser(int userid) {
        Callable<UserEntity> callable = () -> restApi.getSingleUser(userid);
        Future<UserEntity> result = executor.submit(callable);

        UserEntity mUser = null;
        try {
            mUser = result.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }

        User user = userEntityDataMapper.transform(mUser);

        return null;
    }

    @Override
    public User login(final String username, final String password) {
        Callable<UserEntity> callable = () -> restApi.login(username, password);
        Future<UserEntity> result = executor.submit(callable);

        UserEntity mUser = null;
        try {
            mUser = result.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }

        User user = userEntityDataMapper.transform(mUser);

        return user;
    }

    @Override
    public User deleteUser(int userid) {
        return null;
    }

    @Override
    public User createUser(User user) {
        return null;
    }
}
