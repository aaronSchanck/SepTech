package com.septech.centauri.ui.user.forgotpassword;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputLayout;
import com.septech.centauri.R;
import com.septech.centauri.ui.user.forgotpasswordcode.ForgotPasswordCodeActivity;

public class ForgotPasswordActivity extends AppCompatActivity {
    private static final String TAG = "ForgotPasswordActivity";

    private ForgotPasswordViewModel mForgotPasswordViewModel;

    private EditText mEmailEditText;

    private Button mSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpw);

        mForgotPasswordViewModel = new ViewModelProvider(this).get(ForgotPasswordViewModel.class);

        TextInputLayout emailTextInputLayout = findViewById(R.id.forgot_password_textbox);

        mSubmitButton = findViewById(R.id.forgot_password_btn);

        mEmailEditText = emailTextInputLayout.getEditText();

        mEmailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //intentionally left blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //intentionally left blank
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mSubmitButton.setOnClickListener(v -> {
            hideKeyboard();
            mForgotPasswordViewModel.forgotPasswordSubmit(mEmailEditText.getText().toString());
        });

        mForgotPasswordViewModel.getFormLiveData().observe(this, forgotPasswordFormState -> {
            if (forgotPasswordFormState == null) {
                return;
            }

            mSubmitButton.setEnabled(true);

            if (forgotPasswordFormState.isEmailEdited() && forgotPasswordFormState.getEmailError() != null) {
                mEmailEditText.setError(getString(forgotPasswordFormState.getEmailError()));
            } else {
                mEmailEditText.setError(null);
            }
        });

        mForgotPasswordViewModel.getResponseLiveData().observe(this, forgotPasswordCloudResponse -> {
            int duration = Toast.LENGTH_SHORT;

            CharSequence text;
            Toast toast;
            switch(forgotPasswordCloudResponse) {
                case NO_USER_FOUND:
                    text = "No user found with associated email";
                    toast = Toast.makeText(getApplicationContext(), text, duration);
                    toast.show();
                    break;
                case NO_INTERNET:
                    text = "No internet connection";
                    toast = Toast.makeText(getApplicationContext(), text, duration);
                    toast.show();
                    break;
                case LOADING:
                    text = "Connecting to server...";
                    toast = Toast.makeText(getApplicationContext(), text, duration);
                    toast.show();
                    break;
                case USER_FOUND:
                    text = "User found for email";
                    toast = Toast.makeText(getApplicationContext(), text, duration);
                    toast.show();

                    Intent intent = new Intent(this, ForgotPasswordCodeActivity.class);
                    intent.putExtra("email", mEmailEditText.getText().toString());
                    Log.d("INTENT PASSES ", mEmailEditText.getText().toString());
                    startActivity(intent);
                    break;
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
