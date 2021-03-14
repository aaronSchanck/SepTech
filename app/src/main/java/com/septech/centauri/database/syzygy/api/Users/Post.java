package com.septech.centauri.database.syzygy.api.Users;

import android.util.Log;

import com.septech.centauri.database.syzygy.models.User;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class Post {
    private static final String TAG = "centauri-db-api";

    public static boolean createNewUser(User user) {
        return true;
    }

    /**
     * @param username
     * @param password
     * @return
     */
    public User login(String username, String password) {
        HttpsURLConnection con = null;
        User user = null;
        String urlString = Ref.getUserTableURL() + "/login";
        try {
            //create url object
            URL url = new URL(urlString);

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
            user = new User(json);

            Log.i(TAG, result.toString());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.disconnect();
        }
        return user;
    }
}
