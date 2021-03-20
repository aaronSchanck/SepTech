package com.septech.centauri.data.model.user.mapper;

import com.septech.centauri.data.model.user.UserEntity;
import com.septech.centauri.domain.models.User;

public class UserEntityDataMapper {
    public static User transform(UserEntity userEntity) {
        return new User(
                userEntity.getUserId(),
                userEntity.getEmail(),
                userEntity.getFirstName(),
                userEntity.getLastName(),
                userEntity.getDateOfBirth()
        );
    }



    public static UserEntity transform(User user) {
        return new UserEntity(
                user.getEmail(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getDateOfBirth()
        );
    }
}
