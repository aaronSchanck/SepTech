package com.septech.centauri.data.repository;


import android.util.Log;

import com.septech.centauri.data.cache.FileCache;
import com.septech.centauri.data.db.betelgeuse.BetelgeuseDatabase;
import com.septech.centauri.data.model.user.UserEntity;
import com.septech.centauri.data.model.user.mapper.UserEntityDataMapper;
import com.septech.centauri.data.net.RestApiClient;
import com.septech.centauri.data.utils.PasswordUtils;
import com.septech.centauri.domain.models.User;
import com.septech.centauri.domain.repository.UserRepository;
import com.septech.centauri.ui.login.LoginCloudResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class UserDataRepository implements UserRepository {
    private static final String TAG = UserDataRepository.class.getSimpleName();

    private static UserDataRepository mInstance;

    private BetelgeuseDatabase database;
    private final FileCache fileCache;
    private final RestApiClient restApiImpl;
    private final BetelgeuseDatabase localDb;

    private CompositeDisposable mDisposables = new CompositeDisposable();

    private UserDataRepository() {
        this.restApiImpl = RestApiClient.getInstance();
        this.localDb = BetelgeuseDatabase.getDatabase();
        this.fileCache = new FileCache();

        this.mDisposables = new CompositeDisposable();
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
    public Single<User> login(final String email, final String password) {
        PasswordUtils pwUtils = new PasswordUtils(password);
        String pwHash = Arrays.toString(pwUtils.hash());
        return restApiImpl.login(email, password).map(UserEntityDataMapper::transform);
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
        PasswordUtils pwUtils = new PasswordUtils(password);
        String pwHash = Arrays.toString(pwUtils.hash());

        UserEntity userEntity = new UserEntity();

        userEntity.setEmail(email);
        userEntity.setPassword(pwHash);
        userEntity.setFirstName(firstName);
        userEntity.setLastName(lastName);
        userEntity.setPhoneNumber(phoneNumber);
        userEntity.setPasswordSalt(Arrays.toString(pwUtils.getSalt()));

        restApiImpl.createUser(userEntity);
    }

    @Override
    public Single<User> getUserByEmail(String email) {
        return restApiImpl.getUserByEmail(email).map(UserEntityDataMapper::transform);
    }
}
