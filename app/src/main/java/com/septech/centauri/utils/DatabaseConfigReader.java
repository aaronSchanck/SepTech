package com.septech.centauri.utils;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Deprecated
public class DatabaseConfigReader {
    private InputStream input;
    private Properties prop;

    private final String FILELOC = "config/database";
    private final String FILENAME = "login.config";

    public DatabaseConfigReader() {
        readFile();
    }

    public void readFile() {

//        Context context = new Context;
//        try {
//            this.input = new FileInputStream(context.getFilesDir() + FILELOC + "/" + FILENAME);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        this.prop = new Properties();
//
//        try {
//            prop.load(this.input);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public String getLoginUsername() {
//        return prop.getProperty("username");
        return "postgres";
    }

    public String getLoginPassword() {
//        return prop.getProperty("password");
        return "mypassword";
    }
}
