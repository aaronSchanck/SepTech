package com.septech.centauri.ui.register;

import androidx.appcompat.app.AppCompatActivity;
import com.septech.centauri.R;

import android.os.Bundle;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_account);
    }
}