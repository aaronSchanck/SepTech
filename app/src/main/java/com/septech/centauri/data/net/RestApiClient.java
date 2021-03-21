package com.septech.centauri.data.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.septech.centauri.data.model.user.UserEntity;
import com.septech.centauri.domain.models.User;

import java.io.IOException;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestApiClient {
    private final String API_BASE_URL = "https://septech.me/api/";  // base url for our api

    private static RestApiClient instance;                          // singleton instance of class
    private RestApi restApi;                                        // retrofit instance of restapi

    private Gson gson;

    private RestApiClient() {
        gson = new GsonBuilder().disableHtmlEscaping().create();
        final Retrofit retrofit = new Retrofit.Builder().baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        restApi = retrofit.create(RestApi.class);
    }

    public static RestApiClient getInstance() {
        if (instance == null) {
            instance = new RestApiClient();
        }
        return instance;
    }

    public Single<UserEntity> login(@NonNull String username, @NonNull String password) {
        return restApi.login(username, password);
    }

    public Single<UserEntity> createUser(UserEntity userEntity) {
        return restApi.createUser(userEntity);
    }

    public Single<UserEntity> getUserById(int userid) {
        return restApi.getUserById(userid);
    }

    public Single<User> deleteUser(int userid) {
        return restApi.deleteUser(userid);
    }

    public Observable<List<UserEntity>> getAllUsers() {
        return restApi.getAllUsers();
    }

    public Single<UserEntity> getUserByEmail(String email) {
        return restApi.getUserByEmail(email);
    }
}
