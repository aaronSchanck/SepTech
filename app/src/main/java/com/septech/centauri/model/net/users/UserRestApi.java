package com.septech.centauri.model.net.users;


import com.septech.centauri.model.entity.user.UserEntity;


import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserRestApi {

    @POST("/login")
    @FormUrlEncoded
    Call<UserEntity> login(
            @Field("username") String username,
            @Field("password") String password);

    @POST("/users")
    @FormUrlEncoded
    void createUser(@Body UserEntity userEntity);

    @GET("/users/{userid}")
    Observable<UserEntity> getSingleUser(@Path("userid") int userid);

    @GET("/users")
    Observable<List<UserEntity>> getAllUseres();
}
