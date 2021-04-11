package com.septech.centauri.data.net;

import com.septech.centauri.data.model.business.BusinessEntity;
import com.septech.centauri.data.model.item.ItemEntity;
import com.septech.centauri.data.model.user.UserEntity;
import com.septech.centauri.domain.models.Business;
import com.septech.centauri.domain.models.User;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface RestApi {

    //USERS ENDPOINT

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
     * GET endpoint interface method for getting all users from the table.
     * @return All user entities y
     */
    @GET("users")
    Observable<List<UserEntity>> getAllUsers();

    /**
     * Deletes a user based off of their id. If a user with the specified id exists, then the
     * function will delete them from the remote API alongside returning back an observable
     * Single containing the UserEntity model.
     * @param userid
     * @return
     */
    @DELETE("users/{userid}")
    Single<UserEntity> deleteUser(@Path("userid") int userid);

    /**
     *
     * @param email
     * @return
     */
    @GET("users/{email}")
    Single<UserEntity> getUserByEmail(@Path("email") String email);

    @PUT("users/{email}")
    Single<String> forgotPassword(@Path("email") String email);

    @PUT("users/{email}")
    Single<UserEntity> changePassword(@Body UserEntity userEntity, @Path("email") String email);

    /**
     *
     * @param email
     * @return
     */
    @GET("users/{email}/check_exists")
    Single<String> checkExists(@Path("email") String email);

    @FormUrlEncoded
    @POST("users/{email}")
    Single<String> verifyPasswordCode(@Field("code") String code, @Path("email") String email);

    //ITEMS ENDPOINTS

    @Multipart
    @POST("items/create")
    Single<ItemEntity> createItem(@Part MultipartBody.Part[] images,
                                  @Part("itemEntity") ItemEntity itemEntity);

    //BUSINESS ENDPOINTS

    @POST("businesses/login")
    @FormUrlEncoded
    Single<BusinessEntity> businessLogin(
            @Field("email") String email,
            @Field("password") String password);

    @GET("businesses/{email}")
    Single<BusinessEntity> getBusinessByEmail(@Path("email") String email);

    @GET("businesses/{id}")
    Single<BusinessEntity> getBusinessById(@Path("id") int id);

    /**
     * Creates a user based on a UserEntity object.
     * @param businessEntity The UserEntity object to create the user.
     */
    @Headers("Content-Type: application/json")
    @POST("businesses/")
    Single<BusinessEntity> createBusinessAccount(@Body BusinessEntity businessEntity);
}
