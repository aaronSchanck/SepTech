package com.septech.centauri.data.net;

import com.septech.centauri.data.model.user.UserEntity;

import java.io.IOException;

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

    public Single<UserEntity> login(@NonNull String username, @NonNull String password) throws IOException {
        return restApi.login(username, password);
    }
}
