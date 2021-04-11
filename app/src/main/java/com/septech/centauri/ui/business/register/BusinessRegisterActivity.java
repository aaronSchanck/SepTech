package com.septech.centauri.ui.business.register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.septech.centauri.R;
import com.septech.centauri.ui.business.login.BusinessLoginActivity;

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
        createAccountBtn.setOnClickListener(v -> mViewModel.createBusinessAccount(businessNameEditText.getText().toString(),
                businessOwnerNameEditText.getText().toString(),
                emailAddrEditText.getText().toString(),
                passwordEditText.getText().toString(),
                phoneNumberEditText.getText().toString()));
    }

    private void createTextWatchers() {
        //TODO
    }

    private void createLiveDataObservers() {
        mViewModel.getResponseLiveData().observe(this, businessRegisterResponse -> {
            switch (businessRegisterResponse) {
                case EMAIL_EXISTS:
                    break;
                case EMAIL_DOES_NOT_EXIST:
                    break;
                case INFO_INCORRECT:
                    loadingIcon.setVisibility(View.GONE);
                    break;
                case LOADING:
                    loadingIcon.setVisibility(View.VISIBLE);
                    break;
                case SUCCESS:
                    loadingIcon.setVisibility(View.GONE);
                    Intent intent = new Intent(this, BusinessLoginActivity.class);
                    startActivity(intent);
            }
        });
    }
}