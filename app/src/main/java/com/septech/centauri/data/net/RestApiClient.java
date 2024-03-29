package com.septech.centauri.data.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.septech.centauri.data.model.business.BusinessEntity;
import com.septech.centauri.data.model.item.ItemEntity;
import com.septech.centauri.data.model.itemreview.ItemReviewEntity;
import com.septech.centauri.data.model.order.OrderEntity;
import com.septech.centauri.data.model.user.UserEntity;
import com.septech.centauri.data.model.wishlist.WishlistEntity;
import com.septech.centauri.data.model.wishlistitem.WishlistItemEntity;
import com.septech.centauri.domain.models.User;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestApiClient {
//    private final String API_BASE_URL = "https://septech.me/api/";  // base url for our api
    private final String API_BASE_URL = "http://192.168.4.38:5000/api/";  // base url for our api

    private static RestApiClient instance;                          // singleton instance of class
    private RestApi restApi;                                        // retrofit instance of restapi

    private Gson gson;

    private RestApiClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .readTimeout(5, TimeUnit.SECONDS)
                .connectTimeout(5, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();

        gson = new GsonBuilder().disableHtmlEscaping().excludeFieldsWithoutExposeAnnotation().serializeNulls().create();
        final Retrofit retrofit = new Retrofit.Builder().baseUrl(API_BASE_URL)
                .client(okHttpClient)
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

    //USERS
    public Single<UserEntity> login(@NonNull String username, @NonNull String password) {
        return restApi.login(username, password);
    }

    public Single<UserEntity> createUser(UserEntity userEntity) {
        return restApi.createUser(userEntity);
    }

    public Single<UserEntity> getUserById(int userid) {
        return restApi.getUserById(userid);
    }

    public Single<UserEntity> deleteUser(int userid) {
        return restApi.deleteUser(userid);
    }

    public Single<UserEntity> updateUser(int userid, UserEntity userEntity) {
        return restApi.updateUser(userid, userEntity);
    }

    public Observable<List<UserEntity>> getAllUsers() {
        return restApi.getAllUsers();
    }

    public Single<UserEntity> getUserByEmail(String email) {
        return restApi.getUserByEmail(email);
    }

    public Single<String> checkExists(String email) {
        return restApi.checkExists(email);
    }

    public Single<String> verifyPasswordCode(String code, String email) { return restApi.verifyPasswordCode(code, email); }

    //ITEMS

    public Single<ItemEntity> getItemById(int id) {
        return restApi.getItemById(id);
    }

    public Single<ItemEntity> getItemDetails(int id) {
        return restApi.getItemDetails(id);
    }

    public Single<ItemEntity> createItem(List<MultipartBody.Part> images, ItemEntity itemEntity) {
        MultipartBody.Part[] imagesArr = new MultipartBody.Part[images.size()];

        images.toArray(imagesArr);
        return restApi.createItem(imagesArr, itemEntity);
    }

    public Observable<List<ItemEntity>> viewAll(int page) {
        return restApi.viewAll(page);
    }

    public Single<Integer> getAmountItems() {
        return restApi.getAmountItems();
    }

    public Observable<List<ItemEntity>> search(String searchQuery, int page) {
        return restApi.search(searchQuery, page);
    }

    public Single<Integer> getAmountInQuery(String query) {
        return restApi.getAmountInQuery(query);
    }

    public Observable<Response<ResponseBody>> getItemThumbnails(String itemIds) {
        return restApi.getItemThumbnails(itemIds);
    }

    //BUSINESSES

    public Single<BusinessEntity> businessLogin(@NonNull String email, @NonNull String password) {
        return restApi.businessLogin(email, password);
    }

    public Single<BusinessEntity> getBusinessByEmail(String email) {
        return restApi.getBusinessByEmail(email);
    }

    public Single<UserEntity> forgotPassword(String email) {
        return restApi.forgotPassword(email);
    }

    public Single<BusinessEntity> getBusinessById(int id) {
        return restApi.getBusinessById(id);
    }

    public Single<BusinessEntity> createBusinessAccount(BusinessEntity businessEntity) {
        return restApi.createBusinessAccount(businessEntity);
    }

    public Observable<Response<ResponseBody>> getImages(int itemId) {
        return restApi.getImages(itemId);
    }

    public Single<OrderEntity> addToCart(int userid, int itemid, int quantity) {
        return restApi.addToCart(userid, itemid, quantity);
    }

    public Single<OrderEntity> getUserCart(int userId) {
        return restApi.getUserCart(userId);
    }

    public Single<WishlistEntity> addToWishlist(int userId, int itemid) {
        return restApi.addToWishlist(userId, itemid);
    }

    public Single<WishlistEntity> getUserWishlist(int userId) {
        return restApi.getUserWishlist(userId);
    }

    public Single<WishlistItemEntity> getUserWishlistItem(Integer userId, Integer itemId) {
        return restApi.getUserWishlistItem(userId, itemId);
    }

    public Single<ItemReviewEntity> addItemReview(ItemReviewEntity itemReviewEntity) {
        return restApi.addItemReview(itemReviewEntity);
    }
}
