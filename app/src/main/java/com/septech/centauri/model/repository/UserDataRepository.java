package com.septech.centauri.model.repository;


import com.septech.centauri.model.entity.user.UserEntity;
import com.septech.centauri.model.entity.user.mapper.UserEntityDataMapper;
import com.septech.centauri.model.executor.JobExecutor;
import com.septech.centauri.model.net.RestApi;
import com.septech.centauri.model.net.RestApiImpl;
import com.septech.centauri.viewmodel.models.User;
import com.septech.centauri.viewmodel.repository.UserRepository;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class UserDataRepository implements UserRepository {

    private ExecutorService executor;
    private RestApi restApi;
    private UserEntityDataMapper userEntityDataMapper;

    public UserDataRepository() {
        this.executor = Executors.newFixedThreadPool(10);
        restApi = new RestApiImpl();
        userEntityDataMapper = new UserEntityDataMapper();
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

        return null;
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
