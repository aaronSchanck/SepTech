package com.septech.centauri.data.net;

import com.septech.centauri.data.model.user.UserEntity;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RestApi {

    @POST("users/login")
    @FormUrlEncoded
    Single<UserEntity> login(
            @Field("username") String username,
            @Field("password") String password);

    @POST("users")
    @FormUrlEncoded
    void createUser(@Body UserEntity userEntity);

    @GET("users/{userid}")
    Call<UserEntity> getSingleUser(@Path("userid") int userid);

    @GET("users")
    Observable<List<UserEntity>> getAllUsers();
}
