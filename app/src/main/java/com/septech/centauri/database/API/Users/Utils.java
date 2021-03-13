package com.septech.centauri.database.API.Users;

public class Utils {
    private static final String BASEAPIURL = "http://septech.me/api";
    private static final String USERSTABLEAPIURL = "/users";

    static String getUserTableURL() {
        return BASEAPIURL + USERSTABLEAPIURL;
    }
}
