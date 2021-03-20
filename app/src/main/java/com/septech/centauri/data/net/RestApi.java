package com.septech.centauri.data.net;

import androidx.room.Delete;

import com.septech.centauri.data.model.user.UserEntity;
import com.septech.centauri.domain.models.User;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
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
    Single<UserEntity> getUserById(@Path("userid") int userid);

    @GET("users")
    Observable<List<UserEntity>> getAllUsers();

    @DELETE("users/{userid}")
    Single<User> deleteUser(@Path("userid") int userid);

    @GET("users/{email}")
    Single<UserEntity> getUserByEmail(@Path("email") String email);

    @POST("users")
    void createAccount(@Field("email") String email,
                       @Field("password") String password,
                       @Field("first_name") String firstName,
                       @Field("last_name") String lastName,
                       @Field("phone_number") String phoneNumber);
}
