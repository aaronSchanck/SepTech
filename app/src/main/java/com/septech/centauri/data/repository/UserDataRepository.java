package com.septech.centauri.data.repository;

import com.septech.centauri.data.entity.user.UserEntity;
import com.septech.centauri.database.syzygy.models.User;
import com.septech.centauri.domain.repository.UserRepository;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.inject.Singleton;

import io.reactivex.Observable;

public class UserDataRepository implements UserRepository {


    public User getSingleUser(int userid) {
        Callable<User> callable = () -> usersGet.getSingleUser(userid);
        Future<User> result = executor.submit(callable);

        try {
            return result.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
