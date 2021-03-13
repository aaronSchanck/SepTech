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
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class Post {
    private static final String TAG = "centauri-db-api";

    public static boolean createNewUser(User user) {
        return true;
    }

    public User login(String username, String password) {
        HttpsURLConnection con = null;
        User user = null;
        try {
            //create json object and populate

            URL url = new URL(Utils.getUserTableURL() + "/login");

            Map<String, String> postDataParams = new LinkedHashMap<>();
            postDataParams.put("username", username);
            postDataParams.put("password", password);

            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String,String> param : postDataParams.entrySet()) {
                if (postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }

            byte[] postDataBytes = postData.toString().getBytes(StandardCharsets.UTF_8);

            con = (HttpsURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
            con.setDoOutput(true);
            con.setDoInput(true);

            con.getOutputStream().write(postDataBytes);

            InputStream inputStream;
            int status = con.getResponseCode();

            if (status != HttpURLConnection.HTTP_OK)  {
                inputStream = con.getErrorStream();
            }
            else  {
                inputStream = con.getInputStream();
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    inputStream));

            StringBuilder result = new StringBuilder();

            for(String line; (line = reader.readLine()) != null; ) {
                result.append(line).append("\n");
            }

            Log.i(TAG, result.toString());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.disconnect();
        }

        return user;
    }

    private String getPostDataString(List<String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(String s: params){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(s);
        }

        return result.toString();
    }
}
