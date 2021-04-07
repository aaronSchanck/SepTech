package com.septech.centauri.ui.landing;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.septech.centauri.R;
import com.septech.centauri.ui.business.login.BusinessLoginActivity;
import com.septech.centauri.ui.user.login.LoginActivity;

public class LandingActivity extends AppCompatActivity {

    private Button proceedUserBtn;
    private Button proceedBusinessBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        proceedUserBtn = findViewById(R.id.proceedUserBtn);
        proceedBusinessBtn = findViewById(R.id.proceedBusinessBtn);

        createButtonListeners();
    }

    private void createButtonListeners() {
        proceedUserBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });

        proceedBusinessBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, BusinessLoginActivity.class);
            startActivity(intent);
        });
    }
}
