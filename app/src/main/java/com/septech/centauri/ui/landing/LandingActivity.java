package com.septech.centauri.ui.landing;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.septech.centauri.R;

public class LandingActivity extends AppCompatActivity {

    private Button proceedUserBtn;
    private Button proceedBusinessBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        proceedUserBtn = findViewById(R.id.proceedUserBtn);
        proceedBusinessBtn = findViewById(R.id.proceedBusinessBtn );
    }
}
