package com.septech.centauri.ui.chat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;


import com.septech.centauri.R;

import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * A login screen that offers login via jid/password.
 */


public class ChatLoginActivity extends AppCompatActivity
{

    // UI references.
    private TextView mJidView;
    private EditText mPasswordView;

    private View mLoginFormView;
    private BroadcastReceiver mBroadcastReceiver;
    private Context mContext;

    private static final String TAG = "Chat Login Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_login);
        // Set up the login form.
        mJidView = findViewById(R.id.email);

        mPasswordView = findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener((textView, id, keyEvent) -> {
            if (id == R.id.login || id == EditorInfo.IME_NULL) {
                attemptLogin();
                return true;
            }
            return false;
        });

        Button mJidSignInButton = (Button) findViewById(R.id.login);
        mJidSignInButton.setOnClickListener(view -> attemptLogin());
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        // Reset errors.
        mJidView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mJidView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.register_string_password_incorrect));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mJidView.setError(getString(R.string.register_string_email_incorrect));
            focusView = mJidView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mJidView.setError(getString(R.string.register_string_email_incorrect));
            focusView = mJidView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            //do login

            ChatLogin login = new ChatLogin(mJidView.getText().toString(),
                    "Liam Blair",
                    mPasswordView.getText().toString(),
                    this);
            login.saveCredentialsAndLogin();
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.unregisterReceiver(mBroadcastReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                mContext = context;
                switch (action) {
                    case ChatConnectionService.UI_AUTHENTICATED:
                        Log.d(TAG, "Broadcast shows main app window");
                        // Show main app window
                        Intent i2 = new Intent(mContext, MessagesActivity.class);
                        startActivity(i2);
                        finish();
                        break;
                }
            }
        };
        IntentFilter filter = new IntentFilter(ChatConnectionService.UI_AUTHENTICATED);
        this.registerReceiver(mBroadcastReceiver, filter);
    }
}