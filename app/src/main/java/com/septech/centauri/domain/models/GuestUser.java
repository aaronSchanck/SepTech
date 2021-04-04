package com.septech.centauri.domain.models;

public class GuestUser extends User {
    public GuestUser() {
        super(0);
        super.setEmail("guest@guest.com");
        super.setFullName("Guest User");
    }
}
