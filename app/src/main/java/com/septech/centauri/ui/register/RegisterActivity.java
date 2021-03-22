package com.septech.centauri.ui.register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputLayout;
import com.septech.centauri.R;

import android.os.Bundle;
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
    private EditText mLNameEditText;
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

        TextInputLayout fullNameTextInput =
                (TextInputLayout)findViewById(R.id.fname_text_input);
//        TextInputLayout lastNameTextInput = findViewById(R.id.lname_text_input);
        TextInputLayout emailTextInput = findViewById(R.id.email_text_input);
        TextInputLayout passwordTextInput = findViewById(R.id.password_text_input);
        TextInputLayout confirmPasswordTextInput = findViewById(R.id.confirm_password_text_input);
        TextInputLayout phoneTextInput = findViewById(R.id.phone_text_input);

        //TODO: link loading icon to response
        mLoadingIcon = findViewById(R.id.loading_icon);
        mLoadingIcon.setVisibility(View.GONE);

        mRegisterViewModel.getResponseLiveData().observe(this, this::processResponse);

        mFNameEditText = fullNameTextInput.getEditText();
//        mLNameEditText = lastNameTextInput.getEditText();
        mEmailEditText = emailTextInput.getEditText();
        mPasswordEditText = passwordTextInput.getEditText();
        mConfirmPasswordEditText = confirmPasswordTextInput.getEditText();
        mPhoneNumberEditText = phoneTextInput.getEditText();

        //TODO: get birth date from calendar

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

//        mLNameEditText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                // intentionally left empty
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                // intentionally left empty
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                mRegisterViewModel.onUpdateLastName(mLNameEditText.getText().toString());
//            }
//        });

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

        mPhoneNumberEditText.addTextChangedListener(new TextWatcher() {
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
                mRegisterViewModel.onUpdatePhoneNumber(mPhoneNumberEditText.getText().toString());
            }
        });

        mCreateAccountBtn = findViewById(R.id.register_create_account_btn);

        mCreateAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRegisterViewModel.createAccount(
                        mEmailEditText.getText().toString(),
                        mPasswordEditText.getText().toString(),
                        mFNameEditText.getText().toString(),
//                        mLNameEditText.getText().toString(),
                        mPhoneNumberEditText.getText().toString());
            }
        });

        mRegisterViewModel.getRegisterFormState().observe(this, new Observer<RegisterFormState>() {
            @Override
            public void onChanged(RegisterFormState registerFormState) {
                if (registerFormState == null) {
                    return;
                }

                mCreateAccountBtn.setEnabled(true);

                if (registerFormState.isFullNameEdited() && registerFormState.getFullNameError() != null) {
                    mFNameEditText.setError(getString(registerFormState.getFullNameError()));
                } else {
                    mFNameEditText.setError(null);
                }

//                if (registerFormState.isLastNameEdited() && registerFormState.getLastNameError() != null) {
//                    mLNameEditText.setError(getString(registerFormState.getLastNameError()));
//                } else {
//                    mLNameEditText.setError(null);
//                }

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
            }
        });

    }

    private void processResponse(RegisterCloudResponse response) {
        if (response == RegisterCloudResponse.LOADING) {
            showLoadingIcon();
        } else if (response == RegisterCloudResponse.FAILED) {
            hideLoadingIcon();
        } else {
            mLoadingIcon.setVisibility(View.GONE);
            //TODO: change activity
        }
    }

    private void showLoadingIcon() {
        mLoadingIcon.setVisibility(View.VISIBLE);
    }

    private void hideLoadingIcon() {
        mLoadingIcon.setVisibility(View.GONE);
    }
}