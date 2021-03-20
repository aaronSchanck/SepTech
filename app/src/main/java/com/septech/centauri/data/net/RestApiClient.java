package com.septech.centauri.data.net;

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
    private final String API_BASE_URL = "https://septech.me/api/";

    private static RestApiClient instance;
    private RestApi restApi;

    private RestApiClient() {
        final Retrofit retrofit = new Retrofit.Builder().baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
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

    public void createUser(UserEntity userEntity) {
        restApi.createUser(userEntity);
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

    public void createAccount(String email, String password, String firstName, String lastName, String phoneNumber) {
        restApi.createAccount(email, password, firstName, lastName, phoneNumber);
    }

    public Single<UserEntity> getUserByEmail(String email) {
        return restApi.getUserByEmail(email);
    }
}
