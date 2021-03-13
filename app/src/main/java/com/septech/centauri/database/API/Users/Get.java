package com.septech.centauri.database.API.Users;

import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;

import com.septech.centauri.interfaces.Global;
import com.septech.centauri.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Get implements Global {
    private static final String TAG = "centauri-db-api";

    public static User getSingleUser(int userid) {
        URL url = null;
        try {
            url = getTableURL(Integer.toString(userid));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        HttpURLConnection con = null;
        InputStream in = null;
        try {
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            int code = con.getResponseCode();
            String msg = con.getResponseMessage();

            String content = getSingleUserContent(con);

            Log.i(TAG, content);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            con.disconnect();
        }

        return null;
    }

    private static String getSingleUserContent(HttpURLConnection con) throws IOException {
        InputStream in = new BufferedInputStream(con.getInputStream());
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder result = new StringBuilder();

        for(String line; (line = reader.readLine()) != null; ) {
            result.append(line).append("\n");
        }

        String json = result.toString();


        try {
            JSONObject object = object = (JSONObject) new JSONTokener(json).nextValue();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i(TAG, result.toString());

        return result.toString();
    }

    public static User getAllUsers() {
        URL url = null;
        try {
            url = getTableURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        HttpURLConnection con = null;
        InputStream in = null;
        try {
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            String response = getFullResponse(con);

            Log.i(TAG, response);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            con.disconnect();
        }

        return null;
    }

    private static URL getTableURL() throws MalformedURLException {
        return getTableURL("");
    }

    private static URL getTableURL(String append) throws MalformedURLException {
        return new URL(BASEAPIURL + USERTABLEURL + "/" + append);
    }

    public static String getFullResponse(HttpURLConnection con) throws IOException {
        InputStream in = new BufferedInputStream(con.getInputStream());
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder result = new StringBuilder();

        for(String line; (line = reader.readLine()) != null; ) {
            result.append(line);
        }

        Log.i(TAG, result.toString());

        return result.toString();
    }
}
