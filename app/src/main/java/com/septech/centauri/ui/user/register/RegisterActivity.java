package com.septech.centauri.ui.user.register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputLayout;
import com.septech.centauri.R;
import com.septech.centauri.ui.user.login.LoginActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";

    private RegisterViewModel mRegisterViewModel;

    private EditText mFNameEditText;
    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private EditText mConfirmPasswordEditText;
    private EditText mPhoneNumberEditText;
    private Button mCreateAccountBtn;
    private ProgressBar mLoadingIcon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_account);

        mRegisterViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        TextInputLayout fullNameTextInput = findViewById(R.id.fname_text_input);
        TextInputLayout emailTextInput = findViewById(R.id.email_text_input);
        TextInputLayout passwordTextInput = findViewById(R.id.password_text_input);
        TextInputLayout confirmPasswordTextInput = findViewById(R.id.confirm_password_text_input);
        TextInputLayout phoneTextInput = findViewById(R.id.phone_text_input);

        mLoadingIcon = findViewById(R.id.loading_icon);
        mLoadingIcon.setVisibility(View.GONE);

        mRegisterViewModel.getResponseLiveData().observe(this, response -> {
            switch (response) {
                case EMAIL_EXISTS:
                    mEmailEditText.setError("Email already exists");
                    break;
                case EMAIL_DOES_NOT_EXIST:
                    mEmailEditText.setError(null);
                    break;
                case INFO_INCORRECT:
                    hideLoadingIcon();
                    break;
                case LOADING:
                    showLoadingIcon();
                    break;
                case SUCCESS:
                    startLoginActivity();
                    break;
            }
        });

        mFNameEditText = fullNameTextInput.getEditText();
        mEmailEditText = emailTextInput.getEditText();
        mPasswordEditText = passwordTextInput.getEditText();
        mConfirmPasswordEditText = confirmPasswordTextInput.getEditText();
        mPhoneNumberEditText = phoneTextInput.getEditText();

        mCreateAccountBtn = findViewById(R.id.business_register_create_account_btn);

        //TODO: get birth date from calendar

        createLiveDataObservers();

        createTextWatchers();

        createButtonListeners();

        createFocusChangeListeners();
    }

    private void startLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void createFocusChangeListeners() {
        mEmailEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                mRegisterViewModel.checkUserExists(mEmailEditText.getText().toString());
            }
        });
    }

    private void createLiveDataObservers() {
        mRegisterViewModel.getRegisterFormState().observe(this, registerFormState -> {
            if (registerFormState == null) {
                return;
            }

            mCreateAccountBtn.setEnabled(true);

            if (registerFormState.isFullNameEdited() && registerFormState.getFullNameError() != null) {
                mFNameEditText.setError(getString(registerFormState.getFullNameError()));
            } else {
                mFNameEditText.setError(null);
            }

            if (registerFormState.isEmailEdited() && registerFormState.getEmailError() != null) {
                mEmailEditText.setError(getString(registerFormState.getEmailError()));
            } else {
                mEmailEditText.setError(null);
            }

            if (registerFormState.isPasswordEdited() && registerFormState.getPasswordError() != null) {
                mPasswordEditText.setError(getString(registerFormState.getPasswordError()));
            } else {
                mPasswordEditText.setError(null);
            }

            if (registerFormState.isConfirmPasswordEdited() && registerFormState.getConfirmPasswordError() != null) {
                mConfirmPasswordEditText.setError(getString(registerFormState.getConfirmPasswordError()));
            } else {
                mConfirmPasswordEditText.setError(null);
            }

            if (registerFormState.isPhoneNumberEdited() && registerFormState.getPhoneNumberError() != null) {
                mPhoneNumberEditText.setError(getString(registerFormState.getPhoneNumberError()));
            } else {
                mPhoneNumberEditText.setError(null);
            }
        });
    }

    private void createTextWatchers() {
        mFNameEditText.addTextChangedListener(new TextWatcher() {
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
                mRegisterViewModel.onUpdateFullName(mFNameEditText.getText().toString());
            }
        });

        mEmailEditText.addTextChangedListener(new TextWatcher() {
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
                mRegisterViewModel.onUpdateEmail(mEmailEditText.getText().toString());
            }
        });

        mPasswordEditText.addTextChangedListener(new TextWatcher() {
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
                mRegisterViewModel.onUpdatePassword(mPasswordEditText.getText().toString(),
                        mConfirmPasswordEditText.getText().toString());
            }
        });

        mConfirmPasswordEditText.addTextChangedListener(new TextWatcher() {
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
                mRegisterViewModel.onUpdateConfirmPassword(mConfirmPasswordEditText.getText().toString(), mPasswordEditText.getText().toString());
            }
        });

        mPhoneNumberEditText.addTextChangedListener(new PhoneNumberFormattingTextWatcher("1"));
    }

    private void createButtonListeners() {
        mCreateAccountBtn.setOnClickListener(v -> mRegisterViewModel.createAccount(
                mEmailEditText.getText().toString(),
                mPasswordEditText.getText().toString(),
                mFNameEditText.getText().toString(),
                mPhoneNumberEditText.getText().toString()));
    }

    private void showLoadingIcon() {
        mLoadingIcon.setVisibility(View.VISIBLE);
    }

    private void hideLoadingIcon() {
        mLoadingIcon.setVisibility(View.GONE);
    }
}