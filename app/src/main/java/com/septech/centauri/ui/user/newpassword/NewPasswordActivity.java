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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputLayout;
import com.septech.centauri.R;
import com.septech.centauri.ui.user.login.LoginActivity;


public class NewPasswordActivity extends AppCompatActivity {
    private static final String TAG = "NewPasswordActivity";

    private NewPasswordViewModel mViewModel;

    private EditText mNewPasswordEditText;
    private EditText mVerifyNewPasswordEditText;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_user_forgotpw_newpw);

        mViewModel = new ViewModelProvider(this).get(NewPasswordViewModel.class);

        String email = getIntent().getStringExtra("email");
        mViewModel.getUser(email);

        TextInputLayout newPasswordTextInput = findViewById(R.id.EnterNewPasswordInput);
        TextInputLayout verifyNewPasswordTextInput = findViewById(R.id.VerifyNewPasswordInput);

        mNewPasswordEditText = newPasswordTextInput.getEditText();
        mVerifyNewPasswordEditText = verifyNewPasswordTextInput.getEditText();

        Button mVerifyButton = findViewById(R.id.VerifyButton);

        createTextWatchers();

        mVerifyButton.setOnClickListener(v -> {
            hideKeyboard();
            mViewModel.changePassword(mNewPasswordEditText.getText().toString());
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
                mViewModel.onUpdatePassword(mNewPasswordEditText.getText().toString(),
                        mVerifyNewPasswordEditText.getText().toString());
            }
        });

        mViewModel.getResponseLiveData().observe(this, response -> {
            int duration = Toast.LENGTH_SHORT;

            CharSequence text;
            Toast toast;
            switch (response) {     // TODO: include a case for password being the same as previous
                case MISMATCH_PASSWORD:
                    text = "Password does not match";
                    toast = Toast.makeText(getApplicationContext(), text, duration);
                    toast.show();
                case LOADING:
                    break;
                case SUCCESS:
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
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
                mViewModel.onUpdateConfirmPassword(mVerifyNewPasswordEditText.getText().toString(), mNewPasswordEditText.getText().toString());
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
