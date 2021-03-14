package com.septech.centauri.data.net;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.TlsVersion;

public class ApiConnection {
    private static final String CONTENT_TYPE_LABEL = "Content-Type";
    private static final String CONTENT_TYPE_VALUE_JSON = "application/json; charset=utf-8";

    private URL url;
    private final String mode;
    private String response;

    private ApiConnection(String url, String mode) throws MalformedURLException {
        this.url = new URL(url);
        this.mode = mode;
    }

    public static ApiConnection createGET(String url) throws MalformedURLException {
        return new ApiConnection(url, "GET");
    }

    public static ApiConnection createPOST(String url) throws MalformedURLException {
        return new ApiConnection(url, "POST");
    }

    public static ApiConnection createDELETE(String url) throws MalformedURLException {
        return new ApiConnection(url, "DELETE");
    }

    public static ApiConnection createPUT(String url) throws MalformedURLException {
        return new ApiConnection(url, "PUT");
    }

    public void connectToAPIGET() {
        OkHttpClient okHttpClient = this.createClient();

        final Request request = new Request.Builder()
                .url(this.url)
                .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_VALUE_JSON)
                .get()
                .build();
        try {
            this.response = okHttpClient.newCall(request).execute().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private OkHttpClient createClient() {
        final OkHttpClient client = new OkHttpClient();

        return client;
    }

    private ConnectionSpec getConnectionSpec() {
        ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                .tlsVersions(TlsVersion.TLS_1_2)
    }


}
