package com.septech.centauri.ui.business.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.septech.centauri.R;
import com.septech.centauri.ui.business.addlisting.AddListingActivity;

public class BusinessHomeActivity extends AppCompatActivity {

    private BusinessHomeViewModel mBusinessHomeViewModel;

    private TextView businessNameTextView;
    private TextView businessDescTextView;

    private Button addListingBtn;
    private Button myAuctionsBtn;
    private Button ordersBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_home);

        mBusinessHomeViewModel = new ViewModelProvider(this).get(BusinessHomeViewModel.class);

        businessNameTextView = findViewById(R.id.seller_homepage_name_text);
        businessDescTextView = findViewById(R.id.seller_homepage_description_text);

        addListingBtn = findViewById(R.id.addListingBtn);
        myAuctionsBtn = findViewById(R.id.myAuctionsBtn);
        ordersBtn = findViewById(R.id.ordersBtn);

        createButtonListeners();

        createLiveDataObservers();

        mBusinessHomeViewModel.setBusiness(Integer.parseInt(getIntent().getStringExtra("id")));
    }

    private void createButtonListeners() {
        addListingBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddListingActivity.class);
            intent.putExtra("id",
                    String.valueOf(mBusinessHomeViewModel.getBusinessLiveData().getValue().getId()));

            startActivity(intent);
        });

        myAuctionsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
            }
        });

        ordersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
            }
        });
    }

    private void createLiveDataObservers() {
        mBusinessHomeViewModel.getBusinessLiveData().observe(this, business -> {
            if(business == null) return;

            businessNameTextView.setText(business.getBusinessName());
            businessDescTextView.setText(business.getOwnerFullName());
        });
    }
}
