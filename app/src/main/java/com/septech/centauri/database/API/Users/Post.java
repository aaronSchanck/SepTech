package com.septech.centauri.database.API.Users;

import android.util.Log;

import com.septech.centauri.models.User;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Post {
    private static final String TAG = "centauri-db-api";

    public static boolean createNewUser(User user) {
        return true;
    }

    public User login(String username, String password) {
        HttpURLConnection con = null;
        User user = null;
        try {
            //create json object and populate
            JSONObject json = new JSONObject();

            json.put("username", username);
            json.put("password", password);

            URL url = new URL(Utils.getUserTableURL() + "/login");

            con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setChunkedStreamingMode(0);

            OutputStream out = new BufferedOutputStream(con.getOutputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                    out, StandardCharsets.UTF_8));
            writer.write(json.toString());
            writer.flush();

            int code = con.getResponseCode();
            if (!(code >=  200 && code < 300)) {
                throw new IOException("Invalid response from server: " + code);
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));

            StringBuilder result = new StringBuilder();

            for(String line; (line = reader.readLine()) != null; ) {
                result.append(line).append("\n");
            }

            Log.i(TAG, result.toString());

            user = new User(json);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.disconnect();
        }

        return user;
    }
}
