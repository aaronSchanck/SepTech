package com.septech.centauri.lib;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@SuppressLint("SimpleDateFormat")
public class DateTime {
    private static final DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static String nowDate() {
        return dateFormat.format(Calendar.getInstance().getTime());
    }

    public static String nowDateTime() {
        return dateTimeFormat.format(Calendar.getInstance().getTime());
    }

}
