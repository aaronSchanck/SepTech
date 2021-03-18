package com.septech.centauri.data.repository;


import com.septech.centauri.data.datasource.UserDataSource;
import com.septech.centauri.data.db.betelgeuse.BetelgeuseDatabase;
import com.septech.centauri.data.model.user.UserEntity;
import com.septech.centauri.data.model.user.mapper.UserEntityDataMapper;
import com.septech.centauri.data.net.RestApi;
import com.septech.centauri.data.net.RestApiClient;
import com.septech.centauri.domain.models.User;
import com.septech.centauri.domain.repository.UserRepository;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

public class UserDataRepository implements UserRepository {

    private static UserDataRepository mInstance;

    private BetelgeuseDatabase database;
    private RestApi restApi;
    private UserEntityDataMapper userEntityDataMapper;

    private UserDataSource userEntityManager;

    private UserDataRepository() {
        this.userEntityManager = new UserDataSource();

        this.userEntityDataMapper = new UserEntityDataMapper();

//        this.restApi = new RestApiImpl();
//        this.database = BetelgeuseDatabase.getDatabase();
    }

    public static UserDataRepository getInstance() {
        if (mInstance == null) {
            mInstance = new UserDataRepository();
        }
        return mInstance;
    }


    public Observable<User> getSingleUser(int userid) {

        return null;
    }

    @Override
    public Single<User> login(final String username, final String password) {
        try {
            Single<UserEntity> userEntity = RestApiClient.getInstance().login(username,
                    password);
            return userEntity.map(new Function<UserEntity, User>() {
                        @Override
                        public User apply(@NonNull UserEntity userEntity) throws Exception {
                            return UserEntityDataMapper.transform(userEntity);
                        }
                    });

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Observable<User> deleteUser(int userid) {
        return null;
    }

    @Override
    public Observable<User> createUser(User user) {
        return null;
    }
}
