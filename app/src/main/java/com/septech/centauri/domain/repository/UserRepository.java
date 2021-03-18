package com.septech.centauri.domain.repository;


import com.septech.centauri.domain.models.User;

import io.reactivex.Observable;
import io.reactivex.Single;

public interface UserRepository {

    Observable<User> getSingleUser(int userid);

    Single<User> login(String username, String password);

    Observable<User> deleteUser(int userid);

    Observable<User> createUser(User user);
}
