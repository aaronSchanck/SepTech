package com.septech.centauri.ui.user.home;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.septech.centauri.R;

public class HomeActivity extends AppCompatActivity {

    private HomeViewModel mHomeViewModel;

    private ImageButton mViewItemsBtn;
    private ImageButton mCartBtn;
    private ImageButton mWishListBtn;
    private ImageButton mOrdersBtn;
    private ImageButton mViewHistoryBtn;
    private ImageButton mHelpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mHomeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        mViewItemsBtn = findViewById(R.id.homeViewAllItemsBtn);
        mCartBtn = findViewById(R.id.homeCartBtn);
        mWishListBtn = findViewById(R.id.homeWishListBtn);
        mOrdersBtn = findViewById(R.id.homeOrdersBtn);
        mViewHistoryBtn = findViewById(R.id.homeViewHistoryBtn);
        mHelpBtn = findViewById(R.id.homeNeedHelpBtn);

        mViewItemsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mWishListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mOrdersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mViewHistoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mHelpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
