package com.septech.centauri.ui.business.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.septech.centauri.R;
import com.septech.centauri.domain.models.Business;
import com.septech.centauri.domain.models.GuestUser;
import com.septech.centauri.domain.models.User;
import com.septech.centauri.ui.business.home.BusinessHomeActivity;
import com.septech.centauri.ui.business.register.BusinessRegisterActivity;
import com.septech.centauri.ui.user.forgotpassword.ForgotPasswordActivity;
import com.septech.centauri.ui.user.home.HomeActivity;
import com.septech.centauri.ui.user.login.LoginActivity;
import com.septech.centauri.ui.user.login.LoginViewModel;
import com.septech.centauri.ui.user.register.RegisterActivity;

public class BusinessLoginActivity extends AppCompatActivity {

    private BusinessLoginViewModel mLoginViewModel;

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
        setContentView(R.layout.activity_business_login);

        mLoginViewModel = new ViewModelProvider(this).get(BusinessLoginViewModel.class);

        mUsernameTextInput = findViewById(R.id.email_text_input);
        mPasswordTextInput = findViewById(R.id.password_text_input);

        mLoginButton = findViewById(R.id.sign_in_btn);
        mCreateAccountBtn = findViewById(R.id.create_account_btn);
        mForgotPasswordBtn = findViewById(R.id.forgot_password_btn);

        mLoadingIcon = findViewById(R.id.loading_icon);

        mLoadingIcon.setVisibility(View.GONE);

        createLiveDataObservers();

        createFormTextWatchers();

        createButtonListeners();

        mPasswordTextInput.getEditText().setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                mLoginViewModel.login(mUsernameTextInput.getEditText().getText().toString(),
                        mPasswordTextInput.getEditText().getText().toString());
            }
            return false;
        });
    }

    private void createLiveDataObservers() {
        /*
        Observer for the MutableLiveData<User> object. Whenever the User model in the ViewModel
        is updated, this observer is called. Subsequently, the only changes to the User object
        are when the user successfully logs in, so the two tracks of this observer are to
        either: log in as a guest, or log in as a real user.
         */
        mLoginViewModel.getUserLiveData().observe(this, business -> {
            onSuccessfulLogin(business);
        });

        /*
        Observer for the MutableLiveData<LoginCloudResponse> object. Whenever the
        LoginCloudResponse object is updated by the ViewModel, this observer will be called. This
        change will affect the UI as a result.
         */
        mLoginViewModel.getResponseLiveData().observe(this, loginResponse -> {
            switch (loginResponse) {
                case PASSWORD_INCORRECT:
                case NO_BUSINESS_FOUND_FOR_EMAIL:
                    mLoadingIcon.setVisibility(View.GONE);
                    break;
                case NO_INTERNET:
                    break;
                case SEARCHING:
                    mLoadingIcon.setVisibility(View.VISIBLE);
                    break;
                case BUSINESS_FOUND:
                    mLoadingIcon.setVisibility(View.GONE);
                    //TODO need activity to swap to
//                    Intent intent = new Intent(this, BusinessHomeActivity.class);
//                    startActivity(intent);
                    break;
            }
        });

        /*
         Observer for the MutableLiveData<LoginFormState> object. Whenever the LoginFormState
         object is modified, this observer will be called, and the change will affect the UI.
         */
        mLoginViewModel.getLoginFormStateLiveData().observe(this, loginFormState -> {
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
        });
    }

    private void createFormTextWatchers() {
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
    }

    private void createButtonListeners() {
        /*
        This listener is called when the user clicks the "Log in" button. The email and password
        are grabbed from the EditTexts and are subsequently passed to the ViewModel to log the
        user in.
         */
        mLoginButton.setOnClickListener(v -> {
            hideKeyboard();

            String username = mUsernameTextInput.getEditText().getText().toString();
            String password = mPasswordTextInput.getEditText().getText().toString();

            mLoginViewModel.login(username, password);
        });

        /*
        This listener will be called when the "Create an Account" button is clicked. The user
        will be redirected to a the RegisterActivity, in which they can create a new user account
        to login to the app.
         */
        mCreateAccountBtn.setOnClickListener(v -> {
            Intent intent = new Intent(BusinessLoginActivity.this, BusinessRegisterActivity.class);
            startActivity(intent);
        });

        /*
        This listener will be called when the "Forgot your Password" button is clicked. The user
        will be redirected to the ForgotPasswordActivity afterwards, in which they will be
        prompted to enter their email address in order to renew their password.
         */
        mForgotPasswordBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, ForgotPasswordActivity.class);
            startActivity(intent);
        });
    }

    private void onSuccessfulLogin(Business business) {
        Toast.makeText(getApplicationContext(), String.format("Welcome, %s!", business.getEmail()),
                Toast.LENGTH_LONG).show();

        System.out.println("business = " + business);

        Intent intent = new Intent(this, BusinessHomeActivity.class);
        intent.putExtra("id", String.valueOf(business.getId()));

        startActivity(intent);
    }

    private void onUnsuccessfulLogin() {
        Toast.makeText(getApplicationContext(), "Incorrect login", Toast.LENGTH_SHORT).show();
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