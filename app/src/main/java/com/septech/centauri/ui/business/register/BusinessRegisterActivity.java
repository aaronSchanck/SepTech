package com.septech.centauri.ui.business.register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.septech.centauri.R;

public class BusinessRegisterActivity extends AppCompatActivity {

    private BusinessRegisterViewModel mViewModel;

    private EditText businessNameEditText;
    private EditText businessOwnerNameEditText;
    private EditText emailAddrEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private EditText phoneNumberEditText;

    private Button createAccountBtn;

    private ProgressBar loadingIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_register);

        mViewModel = new ViewModelProvider(this).get(BusinessRegisterViewModel.class);

        businessNameEditText = findViewById(R.id.businessNameEditText);
        businessOwnerNameEditText = findViewById(R.id.businessOwnerNameEditText);
        emailAddrEditText = findViewById(R.id.emailAddrEditText);
        passwordEditText = findViewById(R.id.businessRegisterPasswordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        phoneNumberEditText = findViewById(R.id.phoneNumberEditText);

        createAccountBtn = findViewById(R.id.business_register_create_account_btn);

        loadingIcon = findViewById(R.id.loading_icon);

        loadingIcon.setVisibility(View.GONE);

        createButtonListeners();

        createTextWatchers();

        createLiveDataObservers();
    }

    private void createButtonListeners() {
        createAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.createBusinessAccount(businessNameEditText.getText().toString(),
                        businessOwnerNameEditText.getText().toString(),
                        emailAddrEditText.getText().toString(),
                        passwordEditText.getText().toString(),
                        phoneNumberEditText.getText().toString());
            }
        });
    }

    private void createTextWatchers() {
        //TODO
    }

    private void createLiveDataObservers() {

    }
}