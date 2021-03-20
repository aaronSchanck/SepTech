package com.septech.centauri.domain.models;

public class GuestUser extends User {
    public GuestUser() {
        super(0, "guest@septech.com", "Guest", "User", "01-01-1970");
    }
}
