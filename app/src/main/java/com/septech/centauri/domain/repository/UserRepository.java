package com.septech.centauri.domain.repository;


import com.septech.centauri.domain.models.User;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

public interface UserRepository {

    Single<User> getUserById(int userid);

    Single<User> login(String username, String password);

    Single<User> deleteUser(int userid);

    void createUser(User user);

    Observable<List<User>> getAllUsers();

    void createAccount(String email, String password, String firstName, String lastName,
                       String phoneNumber);

    Single<User> getUserByEmail(String email);
}
