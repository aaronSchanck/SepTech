package com.septech.centauri.model.net;

import com.septech.centauri.model.entity.user.UserEntity;
import com.septech.centauri.model.entity.user.mapper.UserEntityJsonMapper;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestApiImpl implements RestApi {

    private final UserEntityJsonMapper userEntityJsonMapper;

    public RestApiImpl() {
        this.userEntityJsonMapper = new UserEntityJsonMapper();
    }

    @Override
    public UserEntity login(String username, String password) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("username", username);
        requestParams.put("password", password);

        try {
            String response = ApiConnection.createPOST(API_URL_LOGIN_USER).syncApiCallPOST(requestParams);

            UserEntity user = this.userEntityJsonMapper.createFromJson(response);

            return user;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public UserEntity getSingleUser(int userid) {

        return null;
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return null;
    }

    @Override
    public void createUser() {

    }

    private Map<String, String> createRequestParams(String... params) {
        return null;
    }
}
