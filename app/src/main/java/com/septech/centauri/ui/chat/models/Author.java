package com.septech.centauri.ui.chat.models;

import com.stfalcon.chatkit.commons.models.IUser;

public class Author implements IUser {
    private String id;
    private String name;
    private String avatar;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getAvatar() {
        return avatar;
    }
}
