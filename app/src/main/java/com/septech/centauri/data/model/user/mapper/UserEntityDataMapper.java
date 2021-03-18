package com.septech.centauri.data.model.user.mapper;

import com.septech.centauri.data.model.user.UserEntity;
import com.septech.centauri.domain.models.User;

public class UserEntityDataMapper {
    public UserEntityDataMapper() {

    }

    public static User transform(UserEntity userEntity) {
        return new User(
                userEntity.getUserId(),
                userEntity.getUsername(),
                userEntity.getEmail(),
                userEntity.getFirstName(),
                userEntity.getLastName(),
                userEntity.getDateOfBirth()
        );
    }
}
