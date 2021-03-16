package com.septech.centauri.model.entity.user.mapper;

import com.septech.centauri.model.entity.user.UserEntity;
import com.septech.centauri.viewmodel.models.User;

public class UserEntityDataMapper {
    public UserEntityDataMapper() {

    }

    public User transform(UserEntity userEntity) {
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
