package com.septech.centauri.database.syzygy.api.Users;

class Ref {
    private static final String BASEAPIURL = "https://septech.me/api";
    private static final String USERSTABLEAPIURL = "/users";

    static String getUserTableURL() {
        return BASEAPIURL + USERSTABLEAPIURL;
    }
}
