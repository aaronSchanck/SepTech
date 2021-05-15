package com.septech.centauri.data.model.user.mapper;

import com.septech.centauri.data.model.user.UserEntity;
import com.septech.centauri.domain.models.User;

public class UserDataMapper {
    public static User transform(UserEntity userEntity) {
        User user = new User(userEntity.getUserId());

        user.setUsername(userEntity.getUsername());
        user.setEmail(userEntity.getEmail());
        user.setPassword(userEntity.getPassword());
        user.setFullName(userEntity.getFullName());
        user.setDateOfBirth(userEntity.getDateOfBirth());
        user.setPhoneNumber(userEntity.getPhoneNumber());
        user.setPasswordSalt(userEntity.getPasswordSalt());
        user.setCreatedAt(userEntity.getCreatedAt());
        user.setModifiedAt(userEntity.getModifiedAt());

        return user;
    }

    public static UserEntity transform(User user) {
        UserEntity userEntity = new UserEntity(user.getId());

        userEntity.setUsername(user.getUsername());
        userEntity.setEmail(user.getEmail());
        userEntity.setPassword(user.getPassword());
        userEntity.setFullName(user.getFullName());
        userEntity.setDateOfBirth(user.getDateOfBirth());
        userEntity.setPhoneNumber(user.getPhoneNumber());
        userEntity.setPasswordSalt(user.getPasswordSalt());
        userEntity.setCreatedAt(user.getCreatedAt());
        userEntity.setModifiedAt(user.getModifiedAt());

        return userEntity;
    }
}
