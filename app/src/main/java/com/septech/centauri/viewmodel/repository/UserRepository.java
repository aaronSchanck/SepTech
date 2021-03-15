package com.septech.centauri.viewmodel.repository;


import com.septech.centauri.viewmodel.models.User;

public interface UserRepository {

    User getSingleUser(int userid);

    User login(String username, String password);

    User deleteUser(int userid);

    User createUser(User user);
}
