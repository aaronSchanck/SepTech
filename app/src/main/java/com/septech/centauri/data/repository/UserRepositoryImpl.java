package com.septech.centauri.data.repository;


import android.service.autofill.UserData;
import android.util.Log;

import com.septech.centauri.data.cache.FileCache;
import com.septech.centauri.data.db.betelgeuse.BetelgeuseDatabase;
import com.septech.centauri.data.model.order.mapper.OrderDataMapper;
import com.septech.centauri.data.model.user.mapper.UserDataMapper;
import com.septech.centauri.data.model.wishlist.mapper.WishlistDataMapper;
import com.septech.centauri.data.model.wishlistitem.mapper.WishlistItemDataMapper;
import com.septech.centauri.data.net.RestApiClient;
import com.septech.centauri.data.utils.PasswordUtils;
import com.septech.centauri.domain.models.Item;
import com.septech.centauri.domain.models.Order;
import com.septech.centauri.domain.models.User;
import com.septech.centauri.domain.models.Wishlist;
import com.septech.centauri.domain.models.WishlistItem;
import com.septech.centauri.domain.repository.UserRepository;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

public class UserRepositoryImpl implements UserRepository {
    private static final String TAG = UserRepositoryImpl.class.getSimpleName();

    private static UserRepositoryImpl mInstance;

    private BetelgeuseDatabase database;
    private final FileCache fileCache;
    private final RestApiClient restApiImpl;
    private final BetelgeuseDatabase localDb;

    private UserRepositoryImpl() {
        this.restApiImpl = RestApiClient.getInstance();
        this.localDb = BetelgeuseDatabase.getDatabase();
        this.fileCache = new FileCache();
    }

    public static UserRepositoryImpl getInstance() {
        if (mInstance == null) {
            mInstance = new UserRepositoryImpl();
        }
        return mInstance;
    }


    public Single<User> getUserById(int userid) {
        try {
            return restApiImpl.getUserById(userid).map(UserDataMapper::transform);
        } catch (Exception e) {
            Log.w(TAG, e.toString());
        }
        return null;
    }

    @Override
    public Single<User> login(final String email, final String password, String passwordSalt) {
        PasswordUtils pwUtils = new PasswordUtils(password, passwordSalt);
        String pwHash = pwUtils.hash();

        return restApiImpl.login(email, pwHash).map(UserDataMapper::transform);
    }

    @Override
    public Single<User> deleteUser(int userid) {
        return restApiImpl.deleteUser(userid).map(UserDataMapper::transform);
    }

    @Override
    public Single<User> update(int userid, User user) {
        return restApiImpl.updateUser(userid, UserDataMapper.transform(user)).map(UserDataMapper::transform);
    }

    @Override
    public void createUser(User user) {
        restApiImpl.createUser(UserDataMapper.transform(user));
    }

    @Override
    public Observable<List<User>> getAllUsers() {
        //return restApi.getAllUsers().map(UserEntityDataMapper::transform);

        //TODO: return list of users
        return null;
    }

    @Override
    public Single<User> createAccount(final User user) {
        return restApiImpl.createUser(UserDataMapper.transform(user)).map(UserDataMapper::transform);
    }

    @Override
    public Single<User> getUserByEmail(String email) {
        return restApiImpl.getUserByEmail(email).map(UserDataMapper::transform);
    }

    @Override
    public Single<String> checkUserExists(String email) {
        return restApiImpl.checkExists(email);
    }

    @Override
    public Single<String> verifyPasswordCode(String code, String email) {
        return restApiImpl.verifyPasswordCode(code, email);
    }

    @Override
    public Single<User> forgotPassword(String email) {
        return restApiImpl.forgotPassword(email).map(UserDataMapper::transform);
    }

    @Override
    public Single<Order> addToCart(User user, Item item, int quantity) {
        return restApiImpl.addToCart(user.getId(), item.getId(), quantity).map(OrderDataMapper::transform);
    }

    @Override
    public Single<Order> getUserCart(int userId) {
        return restApiImpl.getUserCart(userId).map(OrderDataMapper::transform);
    }

    @Override
    public Single<Wishlist> addToWishlist(User user, Item item) {
        return restApiImpl.addToWishlist(user.getId(), item.getId()).map(WishlistDataMapper::transform);
    }

    @Override
    public Single<Wishlist> getUserWishlist(int userId) {
        return restApiImpl.getUserWishlist(userId).map(WishlistDataMapper::transform);
    }

    @Override
    public Single<WishlistItem> getUserWishlistItem(Integer userId, Integer itemId) {
        return restApiImpl.getUserWishlistItem(userId, itemId).map(WishlistItemDataMapper::transform);
    }
}