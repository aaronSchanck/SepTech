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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;

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
    public Single<User> login(final String email, final String password, String passwordSalt) {
        PasswordUtils pwUtils = new PasswordUtils(password, passwordSalt);
        String pwHash = pwUtils.hash();

        return restApiImpl.login(email, pwHash).map(UserEntityDataMapper::transform);
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
    public Single<User> createAccount(String email, String password, String firstName,
                                      String lastName, String phoneNumber) {
        PasswordUtils pwUtils = new PasswordUtils(password);
        String pwHash = pwUtils.hash();

        UserEntity userEntity = new UserEntity();

        userEntity.setEmail(email);
        userEntity.setPassword(pwHash);
        userEntity.setFirstName(firstName);
        userEntity.setLastName(lastName);
        userEntity.setPhoneNumber(phoneNumber);
        userEntity.setPasswordSalt(pwUtils.getSalt());

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        String date = df.format(Calendar.getInstance().getTime());

        DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        String date2 = df2.format(Calendar.getInstance().getTime());

        Log.i("words", pwHash);

        userEntity.setCreatedAt(date);
        userEntity.setModifiedAt(date);
        userEntity.setDateOfBirth(date2);

        return restApiImpl.createUser(userEntity).map(UserEntityDataMapper::transform);
    }

    @Override
    public Single<User> getUserByEmail(String email) {
        return restApiImpl.getUserByEmail(email).map(UserEntityDataMapper::transform);
    }
}
