package com.septech.centauri.model.net;

import android.util.Log;

import com.septech.centauri.model.entity.user.UserEntity;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class RestApiImpl implements RestApi {

    public RestApiImpl() {

    }

    @Override
    public UserEntity login(String username, String password) {
        HttpsURLConnection con = null;
        UserEntity user = null;
        try {
            //create url object
            URL url = new URL(API_URL_LOGIN_USER);

            //create json object and populate
            Map<String, String> postDataParams = new LinkedHashMap<>();
            postDataParams.put("username", username);
            postDataParams.put("password", password);

            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String, String> param : postDataParams.entrySet()) {
                if (postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"))
                        .append("=")
                        .append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }

            byte[] postDataBytes =
                    postData.toString().getBytes(StandardCharsets.UTF_8);

            con = (HttpsURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.setRequestProperty("Content-Length",
                    String.valueOf(postDataBytes.length));
            con.setDoOutput(true);
            con.setDoInput(true);

            con.getOutputStream().write(postDataBytes);

            InputStream inputStream;
            int status = con.getResponseCode();

            if (status != HttpURLConnection.HTTP_OK) {
                inputStream = con.getErrorStream();
            } else {
                inputStream = con.getInputStream();
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    inputStream));
            StringBuilder result = new StringBuilder();

            for (String line; (line = reader.readLine()) != null; ) {
                result.append(line).append("\n");
            }
            JSONObject json = new JSONObject(new JSONTokener(result.toString()));
            user = new UserEntity();

            Log.i(TAG, result.toString());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.disconnect();
        }
        return user;
    }

    @Override
    public UserEntity getSingleUser(int userid) {
//        URL url = null;
//        try {
//            url = new URL(Ref.getUserTableURL() + "/" + userid);
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
//            int code = con.getResponseCode();
//            String msg = con.getResponseMessage();
//
//            String content = getSingleUserContent(con);
//
//            Log.i(TAG, content);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            con.disconnect();
//        }

        return null;
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return null;
    }

    @Override
    public void createUser() {

    }
}
