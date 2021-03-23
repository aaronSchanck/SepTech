package com.septech.centauri.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputLayout;
import com.septech.centauri.R;
import com.septech.centauri.domain.models.User;
import com.septech.centauri.ui.forgotpassword.ForgotPasswordActivity;
import com.septech.centauri.ui.view.activities.MainActivity;
import com.septech.centauri.ui.register.RegisterActivity;


public class LoginActivity extends AppCompatActivity {
    private LoginViewModel mLoginViewModel;

    private TextInputLayout mUsernameTextInput;
    private TextInputLayout mPasswordTextInput;

    private Button mLoginButton;
    private Button mCreateAccountBtn;
    private Button mForgotPasswordBtn;

    private ProgressBar mLoadingIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mUsernameTextInput = findViewById(R.id.email_text_input);
        mPasswordTextInput = findViewById(R.id.password_text_input);
        mLoginButton = findViewById(R.id.sign_in_btn);
        mCreateAccountBtn = findViewById(R.id.create_account_btn);
        mForgotPasswordBtn = findViewById(R.id.forgot_password_btn);
        mLoadingIcon = findViewById(R.id.loading_icon);

        mLoginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        mLoadingIcon.setVisibility(View.GONE);

        mLoginViewModel.getUserLiveData().observe(this, this::processUserResponse);
        mLoginViewModel.getResponseLiveData().observe(this, this::processResponse);
        mLoginViewModel.getLoginFormStateLiveData().observe(this, this::processLoginChange);

        TextWatcher textWatcher = new TextWatcher() {
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
                mLoginViewModel.onUpdateLoginInfo(
                        mUsernameTextInput.getEditText().getText().toString(),
                        mPasswordTextInput.getEditText().getText().toString());
            }
        };

        mUsernameTextInput.getEditText().addTextChangedListener(textWatcher);
        mPasswordTextInput.getEditText().addTextChangedListener(textWatcher);

        bindButtonListeners();

        mPasswordTextInput.getEditText().setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                mLoginViewModel.login(mUsernameTextInput.getEditText().getText().toString(),
                        mPasswordTextInput.getEditText().getText().toString());
            }
            return false;
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void processLoginChange(LoginFormState loginFormState) {
        if (loginFormState == null) {
            return;
        }

        mLoginButton.setEnabled(loginFormState.isDataValid());
        if (loginFormState.getEmailError() != null) {
            mUsernameTextInput.getEditText().setError(getString(loginFormState.getEmailError()));
        }
        if (loginFormState.getPasswordError() != null) {
            mPasswordTextInput.getEditText().setError(getString(loginFormState.getPasswordError()));
        }
    }

    private void processUserResponse(User user) {
        if (user.getUserid() == 0) {
            onGuestLogin(user);
        } else {
            onSuccessfulLogin(user);
        }
    }

    private void processResponse(LoginCloudResponse response) {
        if (response == LoginCloudResponse.LOADING) {
            mUsernameTextInput.getEditText().setText("loading");
            showLoadingIcon();
        } else if (response == LoginCloudResponse.FAILED) {
            hideLoadingIcon();
            mUsernameTextInput.getEditText().setText("failed");
            onUnsuccessfulLogin();
        } else {
            mLoadingIcon.setVisibility(View.GONE);
            changeActivities();
        }
    }

    private void showLoadingIcon() {
        mLoadingIcon.setVisibility(View.VISIBLE);
    }

    private void hideLoadingIcon() {
        mLoadingIcon.setVisibility(View.GONE);
    }

    private void changeActivities() {
        mUsernameTextInput.getEditText().setText("success");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void bindButtonListeners() {
        mLoginButton.setOnClickListener(v -> {
            hideKeyboard();

            String username = mUsernameTextInput.getEditText().getText().toString();
            String password = mPasswordTextInput.getEditText().getText().toString();

            mLoginViewModel.login(username, password);
        });

        mCreateAccountBtn.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        mForgotPasswordBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, ForgotPasswordActivity.class);
            startActivity(intent);
        });
    }


    private void onSuccessfulLogin(User user) {
        Toast.makeText(getApplicationContext(), String.format("Welcome, %s!", user.getFullName()),
                Toast.LENGTH_LONG).show();
        Intent i = new Intent();
    }

    private void onGuestLogin(User user) {

    }

    private void onUnsuccessfulLogin() {
        Toast.makeText(getApplicationContext(), "Incorrect login", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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