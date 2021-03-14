package com.septech.centauri.data.net;

public interface RestApi {
    String TAG = "centauri-db-api";
    String API_BASE_URL = "http://septech.me/api";

    String API_USER_URL = API_BASE_URL + "/users";

    String API_URL_LOGIN_USER = API_USER_URL + "/login";


    String API_URL_CREATE_USER = API_USER_URL;
}
