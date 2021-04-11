package com.septech.centauri.ui.user.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.septech.centauri.R;
import com.septech.centauri.ui.user.search.SearchActivity;

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
        setContentView(R.layout.activity_user_home);

        mHomeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        mViewItemsBtn = findViewById(R.id.homeViewAllItemsBtn);
        mCartBtn = findViewById(R.id.homeCartBtn);
        mWishListBtn = findViewById(R.id.homeWishListBtn);
        mOrdersBtn = findViewById(R.id.homeOrdersBtn);
        mViewHistoryBtn = findViewById(R.id.homeViewHistoryBtn);
        mHelpBtn = findViewById(R.id.homeNeedHelpBtn);

        mViewItemsBtn.setOnClickListener(v -> {
            System.out.println("savedInstanceState = " + savedInstanceState);

            Intent intent = new Intent(this, SearchActivity.class);

            intent.putExtra("query", "");

            startActivity(intent);
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
