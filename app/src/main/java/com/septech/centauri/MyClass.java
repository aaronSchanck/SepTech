package com.septech.centauri;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class MyClass {
    public static void main(String[] args) {
        Connection c;
        Statement stmt;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://104.236.50.161:5432/postgres",
                            "postgres", "mypassword");
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "CREATE TABLE USERS " +
                    "(ID INT PRIMARY KEY     NOT NULL," +
                    " EMAIL          TEXT    NOT NULL, " +
                    " USERNAME       INT     NOT NULL, " +
                    " PASSWORD       CHAR(50), " +
                    " FIRSTNAME      CHAR(50), " +
                    " LASTNAME       TEXT    , " +
                    " DOB            TEXT    , " +
                    " BUSINESS       BOOLEAN)";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        System.out.println("Table created successfully");
    }
}
