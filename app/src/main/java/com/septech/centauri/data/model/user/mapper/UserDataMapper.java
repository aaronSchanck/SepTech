package com.septech.centauri.data.model.user.mapper;

import com.septech.centauri.data.model.user.UserEntity;
import com.septech.centauri.domain.models.User;

public class UserDataMapper {
    public static User transform(UserEntity userEntity) {
        User user = new User(userEntity.getUserid());

        user.setEmail(userEntity.getEmail());
        user.setPassword(userEntity.getPassword());
        user.setFullName(userEntity.getFullName());
        user.setDateOfBirth(userEntity.getDateOfBirth());
        user.setPhoneNumber(userEntity.getPhoneNumber());
        user.setPasswordSalt(userEntity.getPasswordSalt());

        return user;
    }

    public static UserEntity transform(User user) {
        UserEntity userEntity = new UserEntity(user.getId());

        userEntity.setEmail(user.getEmail());
        userEntity.setPassword(user.getPassword());
        userEntity.setFullName(user.getFullName());
        userEntity.setDateOfBirth(user.getDateOfBirth());
        userEntity.setPhoneNumber(user.getPhoneNumber());
        userEntity.setPasswordSalt(user.getPasswordSalt());

        return userEntity;
    }
}
