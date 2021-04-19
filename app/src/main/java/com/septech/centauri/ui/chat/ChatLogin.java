package com.septech.centauri.ui.chat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.septech.centauri.R;
import com.septech.centauri.domain.chat.models.User;

import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;


public class ChatLogin {

    private String username;
    private String fullName;
    private String password;

    private Context mContext;

    private static final String TAG = "ChatLogin";

    public ChatLogin(String username, String fullName, String password, Context context) {
        this.username = username;
        this.fullName = fullName;
        this.password = password;
        User user = new User(username, fullName, null);
        mContext = context;
    }

    public void saveCredentialsAndLogin() {
        Log.d(TAG, "saveCredentialsAndLogin()");
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        prefs.edit()
                .putString("xmpp_jid", username)
                .putString("xmpp_password", password)
                .putBoolean("xmpp_logged_in", true)
                .apply();

        //start service
        Intent il = new Intent(mContext, ChatConnectionService.class);
        mContext.startService(il);
    }
}
