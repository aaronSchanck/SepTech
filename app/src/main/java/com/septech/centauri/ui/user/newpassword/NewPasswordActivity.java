package com.septech.centauri.ui.user.newpassword;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputLayout;
import com.septech.centauri.R;
import com.septech.centauri.ui.user.forgotpasswordcode.ForgotPasswordCodeViewModel;
import com.septech.centauri.ui.user.login.LoginActivity;
import com.septech.centauri.ui.user.register.RegisterViewModel;


public class NewPasswordActivity extends AppCompatActivity {
    private static final String TAG = "ForgotPassword_NewPassword_Activity";

    private NewPasswordViewModel mNewPasswordViewModel;

    private EditText mNewPasswordEditText;
    private EditText mVerifyNewPasswordEditText;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_user_forgotpw_newpw);

        mNewPasswordViewModel = new ViewModelProvider(this).get(NewPasswordViewModel.class);

        TextInputLayout newPasswordTextInput = findViewById(R.id.EnterNewPasswordInput);
        TextInputLayout verifyNewPasswordTextInput = findViewById(R.id.VerifyNewPasswordInput);

        Button mVerifyButton = findViewById(R.id.VerifyButton);

        String userEmail = getIntent().getStringExtra("email");

        mVerifyButton.setOnClickListener(v -> {
            hideKeyboard();
            mNewPasswordViewModel.changePassword(mVerifyNewPasswordEditText.getText().toString(), userEmail);
        });
    }

    private void startLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void createTextWatchers() {
        mNewPasswordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // intentionally left empty
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // intentionally left empty
            }

            @Override
            public void afterTextChanged(Editable s) {
                mNewPasswordViewModel.onUpdatePassword(mNewPasswordEditText.getText().toString(),
                        mVerifyNewPasswordEditText.getText().toString());
            }
        });

        mNewPasswordViewModel.getResponseLiveData().observe(this, response -> {
            switch (response) {     // TODO: include a case for password being the same as previous
                case MISMATCH_PASSWORD:
                    break;
                case LOADING:
                    break;
                case SUCCESS:
                    // TODO: return to login page
                    break;
            }
        });

        mVerifyNewPasswordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // intentionally left empty
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // intentionally left empty
            }

            @Override
            public void afterTextChanged(Editable s) {
                mNewPasswordViewModel.onUpdateConfirmPassword(mVerifyNewPasswordEditText.getText().toString(), mNewPasswordEditText.getText().toString());
            }
        });
    }

    private void hideKeyboard() {
        InputMethodManager imm =
                (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = this.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
