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
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RestApi {

    /**
     * POST endpoint interface method for logging in a user to the server. The method takes the
     * user's email and password and will return the UserEntity object on the server, if it exists.
     * @param email User email to login with.
     * @param password User password to login with.
     * @return UserEntity object representing the user received.
     */
    @POST("users/login")
    @FormUrlEncoded
    Single<UserEntity> login(
            @Field("email") String email,
            @Field("password") String password);

    /**
     * Creates a user based on a UserEntity object.
     * @param userEntity The UserEntity object to create the user.
     */
    @Headers("Content-Type: application/json")
    @POST("users/")
    Single<UserEntity> createUser(@Body UserEntity userEntity);

    /**
     * GET endpoint interface method for getting a user by their userid. Mostly used as a
     * private method for some functionalities.
     * @param userid The user's userid in the Users table.
     * @return A UserEntity representing the user object in the table.
     */
    @GET("users/{userid}")
    Single<UserEntity> getUserById(@Path("userid") int userid);

    /**
     *
     * @return
     */
    @GET("users")
    Observable<List<UserEntity>> getAllUsers();

    /**
     *
     * @param userid
     * @return
     */
    @DELETE("users/{userid}")
    Single<User> deleteUser(@Path("userid") int userid);

    /**
     *
     * @param email
     * @return
     */
    @GET("users/{email}")
    Single<UserEntity> getUserByEmail(@Path("email") String email);
}
