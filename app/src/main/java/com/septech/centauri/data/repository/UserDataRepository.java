package com.septech.centauri.data.repository;


import android.util.Log;

import com.septech.centauri.data.cache.FileCache;
import com.septech.centauri.data.db.betelgeuse.BetelgeuseDatabase;
import com.septech.centauri.data.model.user.mapper.UserEntityDataMapper;
import com.septech.centauri.data.net.RestApiClient;
import com.septech.centauri.data.utils.PasswordUtils;
import com.septech.centauri.domain.models.User;
import com.septech.centauri.domain.repository.UserRepository;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

public class UserDataRepository implements UserRepository {
    private static final String TAG = UserDataRepository.class.getSimpleName();

    private static UserDataRepository mInstance;

    private BetelgeuseDatabase database;
    private final FileCache fileCache;
    private final RestApiClient restApiImpl;
    private final BetelgeuseDatabase localDb;

    private UserDataRepository() {
        this.restApiImpl = RestApiClient.getInstance();
        this.localDb = BetelgeuseDatabase.getDatabase();
        this.fileCache = new FileCache();
    }

    public static UserDataRepository getInstance() {
        if (mInstance == null) {
            mInstance = new UserDataRepository();
        }
        return mInstance;
    }


    public Single<User> getUserById(int userid) {
        try {
            return restApiImpl.getUserById(userid).map(UserEntityDataMapper::transform);
        } catch (Exception e) {
            Log.w(TAG, e.toString());
        }
        return null;
    }

    @Override
    public Single<User> login(final String username, final String password) {
        PasswordUtils pwUtils = new PasswordUtils(password);
        String pwHash = Arrays.toString(pwUtils.hash());
        return restApiImpl.login(username, password).map(UserEntityDataMapper::transform);
    }

    @Override
    public Single<User> deleteUser(int userid) {
        return restApiImpl.deleteUser(userid);
    }

    @Override
    public void createUser(User user) {
        restApiImpl.createUser(UserEntityDataMapper.transform(user));
    }

    @Override
    public Observable<List<User>> getAllUsers() {
        //return restApi.getAllUsers().map(UserEntityDataMapper::transform);

        //TODO: return list of users
        return null;
    }

    @Override
    public void createAccount(String email, String password, String firstName,
                              String lastName, String phoneNumber) {
        restApiImpl.createAccount(email, password, firstName, lastName, phoneNumber);
    }

    @Override
    public Single<User> getUserByEmail(String email) {
        return restApiImpl.getUserByEmail(email).map(UserEntityDataMapper::transform);
    }
}
