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
}
