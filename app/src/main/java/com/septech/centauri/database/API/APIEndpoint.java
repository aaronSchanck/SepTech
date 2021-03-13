package com.septech.centauri.database.API;

import java.net.URL;

public abstract class APIEndpoint {
    protected static final String TAG = "centauri-db-api";
    protected final String BASEAPIURL = "http://septech.me/api";

    protected String getURLString() {
        return BASEAPIURL;
    }
}
