package com.septech.centauri.ui.home;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.septech.centauri.R;

public class HomeActivity extends AppCompatActivity {

    private HomeViewModel mHomeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mHomeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
    }
}
