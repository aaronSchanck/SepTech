package com.septech.centauri.ui.user.login;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProvider;

import com.septech.centauri.R;
import com.septech.centauri.ui.interfaces.CallBackListener;
import com.septech.centauri.ui.user.settings.SettingsViewModel;

/**
 * An activity representing the login UI/UX for the app end-user. The page consists of fields for
 * the user to login by entering their email/password. Other possible user interactions and
 * navigation pathways include:
 *
 * "Forgot your password?": Takes the user to the ForgotPasswordActivity
 * "Continue as Guest": Logs in the user as a Guest user, with less functionality on the app than
 * a standard logged in user.
 * "Create an Account": Redirects the user to the CreateAccount activity, where they can register
 * for a new account.
 */
public class LoginActivity extends AppCompatActivity implements CallBackListener {
    private LoginViewModel mLoginViewModel;
    private SettingsViewModel mSettingsViewModel;

    private ProgressBar mLoadingIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        mLoginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        mSettingsViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);

        mLoadingIcon = findViewById(R.id.loading_icon);

        hideLoadingIcon();
        if(savedInstanceState == null) {
            LoginFragment fragment = LoginFragment.newInstance();
        }
    }

    private void onSuccessfulLogin(User user) {
        Toast.makeText(getApplicationContext(), String.format("Welcome, %s!", user.getFullName()),
                Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("id", user.getId());
        intent.putExtra("name", user.getFullName());

        startActivity(intent);
    }

    private void onUnsuccessfulLogin() {
        Toast.makeText(getApplicationContext(), "Incorrect login", Toast.LENGTH_SHORT).show();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.user_login_content_fragment, fragment)
                    .commit();
        }
    }

    @Override
    public void showLoadingIcon() {
        mLoadingIcon.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingIcon() {
        mLoadingIcon.setVisibility(View.GONE);
    }

    @Override
    public void hideKeyboard() {
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

    @Override
    public void initFragment() {
        hideLoadingIcon();
    }
}