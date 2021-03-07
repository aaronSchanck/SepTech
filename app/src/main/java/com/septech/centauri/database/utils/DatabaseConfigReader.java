package com.septech.centauri.database.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseConfigReader {
    private InputStream input;
    private Properties prop;

    private final String FILELOC = "database/utils";
    private final String FILENAME = "login.config";

    public DatabaseConfigReader() {
        readFile();
    }

    public void readFile() {
        try {
            this.input = new FileInputStream(FILELOC + "/" + FILENAME);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.prop = new Properties();

        try {
            prop.load(this.input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getLoginUsername() {
        return prop.getProperty("username");
    }

    public String getLoginPassword() {
        return prop.getProperty("password");
    }
}
