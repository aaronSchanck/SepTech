package com.septech.centauri.domain.repository;

import com.septech.centauri.database.syzygy.models.User;

import java.util.Observable;

public interface UserRepository {

    User getSingleUser(int userid);

    User login(String username, String password);

    User deleteUser(int userid);

    User createUser(User user);
}
