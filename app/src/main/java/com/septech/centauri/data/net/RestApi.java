package com.septech.centauri.data.net;

import com.septech.centauri.data.model.user.UserEntity;
import com.septech.centauri.domain.models.User;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RestApi {

    /**
     * POST endpoint interface method for logging in a user to the server. The method takes the
     * user's email and password and will return the UserEntity object on the server, if it exists.
     *
     * @param email User email to login with
     * @param password User password to login with
     * @return UserEntity object representing the user received.
     */
    @POST("users/login")
    @FormUrlEncoded
    Single<UserEntity> login(
            @Field("email") String email,
            @Field("password") String password);

    /**
     *
     * @param userEntity
     */
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
