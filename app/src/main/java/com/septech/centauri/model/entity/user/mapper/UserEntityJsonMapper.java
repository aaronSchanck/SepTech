package com.septech.centauri.model.entity.user.mapper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.septech.centauri.model.entity.user.UserEntity;

import java.lang.reflect.Type;

public class UserEntityJsonMapper {

    private final Gson gson;

    public UserEntityJsonMapper() {
        this.gson = new Gson();
    }

    public UserEntity createFromJson(String response) {
        final Type userEntityType = new TypeToken<UserEntity>() {}.getType();
        return this.gson.fromJson(response, userEntityType);
    }
}
