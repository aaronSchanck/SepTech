package com.septech.centauri.database.syzygy.api.Users;

import android.util.Log;

import com.septech.centauri.database.syzygy.api.APIEndpoint;
import com.septech.centauri.database.syzygy.models.User;

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

public class Get extends APIEndpoint {

    public User getSingleUser(int userid) {
        URL url = null;
        try {
            url = new URL(Ref.getUserTableURL() + "/" + userid);
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

    private String getSingleUserContent(HttpURLConnection con) throws IOException {
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

    public User getAllUsers() {
//        URL url = null;
//        try {
//            url = getTableURL();
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//
//        HttpURLConnection con = null;
//        InputStream in = null;
//        try {
//            con = (HttpURLConnection) url.openConnection();
//            con.setRequestMethod("GET");
//
//            String response = getFullResponse(con);
//
//            Log.i(TAG, response);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            con.disconnect();
//        }

        return null;
    }
}
