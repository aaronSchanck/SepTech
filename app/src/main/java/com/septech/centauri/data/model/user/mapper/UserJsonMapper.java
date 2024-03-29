package com.septech.centauri.data.model.user.mapper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.septech.centauri.data.model.user.UserEntity;

import java.lang.reflect.Type;

public class UserJsonMapper {

    private final Gson gson;

    public UserJsonMapper() {
        this.gson = new Gson();
    }

    public UserEntity createFromJson(String response) {
        final Type userEntityType = new TypeToken<UserEntity>() {}.getType();
        return this.gson.fromJson(response, userEntityType);
    }
}
