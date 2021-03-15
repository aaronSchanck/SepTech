package com.septech.centauri.model.net;


import androidx.annotation.Nullable;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.Map;


import okhttp3.ConnectionSpec;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
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

    @Nullable
    String syncApiCallGET() {
        connectToAPIGET();
        return this.response;
    }

    public void connectToAPIGET() {
        OkHttpClient okHttpClient = this.createClient();

        final Request request = this.constructGETRequest();
        try {
            this.response = okHttpClient.newCall(request).execute().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Request constructGETRequest() {
        final Request request = new Request.Builder()
                .url(this.url)
                .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_VALUE_JSON)
                .get()
                .build();

        return request;
    }

    @Nullable
    String syncApiCallPOST(Map<String, String> params) {
        connectToAPIPOST(params);
        return this.response;
    }

    public void connectToAPIPOST(Map<String, String> params) {
        OkHttpClient okHttpClient = this.createClient();

        RequestBody formBody = new FormBody.Builder()
                .add("username", "string")
                .add("password", "string")
                .build();

        final Request request = this.constructPOSTRequest(formBody);
        try {
            this.response = okHttpClient.newCall(request).execute().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Request constructPOSTRequest(RequestBody formBody) {
        final Request request = new Request.Builder()
                .url(this.url)
                .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_VALUE_JSON)
                .post(formBody)
                .build();

        return request;
    }

    public void connectToAPIDELETE() {
        OkHttpClient okHttpClient = this.createClient();

        RequestBody formBody = new FormBody.Builder()
                .add("username", "string")
                .add("username", "string")
                .build();

        final Request request = this.constructDELETERequest(formBody);
        try {
            this.response = okHttpClient.newCall(request).execute().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Request constructDELETERequest(RequestBody formBody) {
        final Request request = new Request.Builder()
                .url(this.url)
                .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_VALUE_JSON)
                .delete(formBody)
                .build();

        return request;
    }

    public void connectToAPIPUT() {
        OkHttpClient okHttpClient = this.createClient();

        RequestBody formBody = new FormBody.Builder()
                .add("username", "string")
                .add("username", "string")
                .build();

        final Request request = this.constructPUTRequest(formBody);
        try {
            this.response = okHttpClient.newCall(request).execute().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Request constructPUTRequest(RequestBody formBody) {
        final Request request = new Request.Builder()
                .url(this.url)
                .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_VALUE_JSON)
                .put(formBody)
                .build();

        return request;
    }

    private OkHttpClient createClient() {
        final OkHttpClient client = new OkHttpClient
                .Builder()
                .connectionSpecs(Collections.singletonList(this.getConnectionSpec()))
                .build();

        return client;
    }

    private ConnectionSpec getConnectionSpec() {
        ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                .tlsVersions(TlsVersion.TLS_1_2)
                .build();

        return spec;
    }
}
