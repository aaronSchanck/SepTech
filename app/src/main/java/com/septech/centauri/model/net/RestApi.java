package com.septech.centauri.model.net;

import com.septech.centauri.model.entity.user.UserEntity;

import java.util.List;

public interface RestApi {
    String TAG = "centauri-db-api";
    String API_BASE_URL = "https://septech.me/api";

    String API_USER_URL = API_BASE_URL + "/users";

    String API_URL_LOGIN_USER = API_USER_URL + "/login";


    String API_URL_CREATE_USER = API_USER_URL;

    UserEntity login(final String username, final String password);

    UserEntity getSingleUser(final int userid);

    List<UserEntity> getAllUsers();

    void createUser();
}
