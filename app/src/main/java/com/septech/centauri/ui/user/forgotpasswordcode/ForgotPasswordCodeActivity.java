package com.septech.centauri.ui.user.forgotpasswordcode;

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
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputLayout;
import com.septech.centauri.R;

public class ForgotPasswordCodeActivity extends AppCompatActivity {
    private static final String TAG = "ForgotPasswordCodeActivity";

    private ForgotPasswordCodeViewModel mForgotPasswordCodeViewModel;

    private EditText mCodeEditText;

    private Button mVerifyButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpw_verify);

        mForgotPasswordCodeViewModel = new ViewModelProvider(this).get(ForgotPasswordCodeViewModel.class);

        TextInputLayout codeInputLayout = findViewById(R.id.EnterCodeTextbox);

        mVerifyButton = findViewById(R.id.VerifyButton);

        mCodeEditText = codeInputLayout.getEditText();

        mCodeEditText.addTextChangedListener(new TextWatcher() {
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

        // TODO: fix verify code
        mVerifyButton.setOnClickListener(v -> {
            hideKeyboard();
            ForgotPasswordCodeViewModel.verifyCodeSubmit(mCodeEditText.getText().toString());
        });

        mForgotPasswordCodeViewModel.getFormLiveData().observe(this, forgotPasswordCodeFormState -> {
            if (forgotPasswordCodeFormState == null) {
                return;
            }

            mVerifyButton.setEnabled(true);

            if (forgotPasswordCodeFormState.isCodeEdited() && forgotPasswordCodeFormState.getCodeError() != null) {
                mCodeEditText.setError(getString(forgotPasswordCodeFormState.getCodeError()));
            } else {
                mCodeEditText.setError(null);
            }
        });

        mForgotPasswordCodeViewModel.getResponseLiveData().observe(this, forgotPasswordCodeCloudResponse -> {
            int duration = Toast.LENGTH_SHORT;

            CharSequence text;
            Toast toast;
            switch(forgotPasswordCodeCloudResponse) {
                // TODO: change switch cases
                case NO_CODE:
                    text = "Incorrect code";
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
                case CODE_FOUND:
                    text = "Code matched";
                    toast = Toast.makeText(getApplicationContext(), text, duration);
                    toast.show();

                    Intent intent = new Intent(this, ForgotPasswordCodeActivity.class);
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
