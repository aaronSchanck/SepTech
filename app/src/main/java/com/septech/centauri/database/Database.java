package com.septech.centauri.database;

import com.septech.centauri.database.utils.DatabaseConfigReader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private Connection connection;

    private String host;
    private String databaseName;
    private final int port = 5432;
    private String user;
    private String password;

    private String url = "jdbc:postgresql://%s:%d/%s";

    private boolean status;

    public Database(String host, String database) {
        this.host = host;
        this.databaseName = database;

        this.url = String.format(this.url, this.host, this.port, this.databaseName);

        DatabaseConfigReader dbcreader = new DatabaseConfigReader();
        user = dbcreader.getLoginUsername();
        password = dbcreader.getLoginPassword();

        connect();
    }

    private void connect() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Class.forName("org.postgresql.Driver");

                    if (user == null || password == null) {
                        DatabaseConfigReader dbcreader = new DatabaseConfigReader();
                        user = dbcreader.getLoginUsername();
                        password = dbcreader.getLoginPassword();
                    }
                    connection = DriverManager.getConnection(url, user, password);

                    status = true;
                } catch (Exception e) {
                    status = false;
                    System.out.print(e.getMessage());
                    e.printStackTrace();
                }
            }
        });
        thread.start();

        try {
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
            this.status = false;
        }
    }

    public Connection getConnection() {
        Connection c = null;
        try {
            Class.forName("org.postgresql.Driver");

            if (user == null || password == null) {
                DatabaseConfigReader dbcreader = new DatabaseConfigReader();
                user = dbcreader.getLoginUsername();
                password = dbcreader.getLoginPassword();
            }
            c = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return c;
    }
}
