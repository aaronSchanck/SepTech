package com.septech.centauri.database;

import android.os.Build;
import android.util.Log;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;

public class Database {
    private final String TAG = "centauri-Database";

    private final int port = 5432;
    private String user;
    private String password;

    public Database() {
        connect();
    }

    private void connect() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                URL url = null;
                try {
                    url = new URL("http://104.236.50.161:5000/api/users/");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                HttpURLConnection urlConnection = null;
                InputStream in = null;
                try {
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");



                    String response = getFullResponse(urlConnection);

                    Log.i(TAG, response);

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    urlConnection.disconnect();
                }
            }
        });
        thread.start();

        try {
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
//        Connection c = null;
//        try {
//            Class.forName("org.postgresql.Driver");
//
//            if (user == null || password == null) {
//                DatabaseConfigReader dbcreader = new DatabaseConfigReader();
//                user = dbcreader.getLoginUsername();
//                password = dbcreader.getLoginPassword();
//            }
//            c = DriverManager.getConnection(url, user, password);
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//        return c;
        return null;
    }

    public String getFullResponse(HttpURLConnection con) throws IOException {
        StringBuilder fullResponseBuilder = new StringBuilder();

        fullResponseBuilder.append(con.getResponseCode())
                .append(" ")
                .append(con.getResponseMessage())
                .append("\n");

        InputStream in = new BufferedInputStream(con.getInputStream());
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder result = new StringBuilder();
        String line;
        while((line = reader.readLine()) != null) {
            result.append(line + "\n");
        }

        Log.i(TAG, result.toString());

        return fullResponseBuilder.toString();
    }
}
