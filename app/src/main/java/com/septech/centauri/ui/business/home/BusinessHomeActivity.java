package com.septech.centauri.ui.business.home;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.septech.centauri.R;

public class BusinessHomeActivity extends AppCompatActivity {

    private BusinessHomeViewModel mBusinessHomeViewModel;

    private TextView businessNameTextView;
    private TextView businessDescTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_home);

        mBusinessHomeViewModel = new ViewModelProvider(this).get(BusinessHomeViewModel.class);

        businessNameTextView = findViewById(R.id.seller_homepage_name_text);
        businessDescTextView = findViewById(R.id.seller_homepage_description_text);
        
        createButtonListeners();

        createLiveDataObservers();

        mBusinessHomeViewModel.setBusiness(Integer.parseInt(getIntent().getStringExtra("id")));
    }

    private void createButtonListeners() {

    }

    private void createLiveDataObservers() {
        mBusinessHomeViewModel.getBusinessLiveData().observe(this, business -> {
            if(business == null) return;

            businessNameTextView.setText(business.getBusinessName());
            businessDescTextView.setText(business.getOwnerFullName());
        });
    }
}
