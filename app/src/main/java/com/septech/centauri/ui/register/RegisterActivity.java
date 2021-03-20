package com.septech.centauri.ui.register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputLayout;
import com.septech.centauri.R;

import android.app.AutomaticZenRule;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_account);

        mRegisterViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        TextInputLayout firstNameTextInput =
                (TextInputLayout)findViewById(R.id.fname_text_input);
        TextInputLayout lastNameTextInput = findViewById(R.id.lname_text_input);
        TextInputLayout emailTextInput = findViewById(R.id.email_text_input);
        TextInputLayout passwordTextInput = findViewById(R.id.password_text_input);
        TextInputLayout confirmPasswordTextInput = findViewById(R.id.confirm_password_text_input);
        TextInputLayout phoneTextInput = findViewById(R.id.phone_text_input);

        mFNameEditText = firstNameTextInput.getEditText();
        mLNameEditText = lastNameTextInput.getEditText();
        mEmailEditText = emailTextInput.getEditText();
        mPasswordEditText = passwordTextInput.getEditText();
        mConfirmPasswordEditText = confirmPasswordTextInput.getEditText();
        mPhoneNumberEditText = phoneTextInput.getEditText();

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
                mRegisterViewModel.onUpdateFirstName(mFNameEditText.getText().toString());
            }
        });

        mLNameEditText.addTextChangedListener(new TextWatcher() {
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
                mRegisterViewModel.onUpdateLastName(mLNameEditText.getText().toString());
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
                        mFNameEditText.getText().toString(), mLNameEditText.getText().toString(),
                        mPhoneNumberEditText.getText().toString());
            }
        });

        mRegisterViewModel.getRegisterFormState().observe(this, new Observer<RegisterFormState>() {
            @Override
            public void onChanged(RegisterFormState registerFormState) {
                if (registerFormState == null) {
                    return;
                }

                mCreateAccountBtn.setEnabled(registerFormState.isDataValid());

                if (registerFormState.isFirstNameEdited() && registerFormState.getFirstNameError() != null) {
                    mFNameEditText.setError(getString(registerFormState.getFirstNameError()));
                } else {
                    mFNameEditText.setError(null);
                }

                if (registerFormState.isLastNameEdited() && registerFormState.getLastNameError() != null) {
                    mLNameEditText.setError(getString(registerFormState.getLastNameError()));
                } else {
                    mLNameEditText.setError(null);
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
            }
        });

    }
}