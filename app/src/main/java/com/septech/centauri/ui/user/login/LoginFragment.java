package com.septech.centauri.ui.user.login;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.septech.centauri.R;
import com.septech.centauri.domain.models.GuestUser;
import com.septech.centauri.domain.models.User;
import com.septech.centauri.ui.user.forgotpassword.ForgotPasswordEmailFragment;
import com.septech.centauri.ui.user.home.CallBackListener;
import com.septech.centauri.ui.user.home.HomeActivity;
import com.septech.centauri.ui.user.register.RegisterFragment;

public class LoginFragment extends Fragment {

    private LoginViewModel mViewModel;
    private UserLoginViewModel mUserLoginViewModel;

    private CallBackListener callBackListener;

    private EditText mEmailEditText;
    private EditText mPasswordEditText;

    private Button mLoginButton;
    private Button mCreateAccountBtn;
    private Button mForgotPasswordBtn;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            callBackListener = (CallBackListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement CallBackListener");
        }
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_login_fragment, container, false);

        mEmailEditText = view.findViewById(R.id.login_user_email_edittext);
        mPasswordEditText = view.findViewById(R.id.login_user_password_edittext);

        mLoginButton = view.findViewById(R.id.sign_in_btn);
        mCreateAccountBtn = view.findViewById(R.id.create_account_btn);
        mForgotPasswordBtn = view.findViewById(R.id.forgot_password_btn);

        createFormTextWatchers();

        createButtonListeners();

        mPasswordEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                mViewModel.login(mEmailEditText.getText().toString(),
                        mPasswordEditText.getText().toString());
            }
            return false;
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        createLiveDataObservers();
    }

    private void createLiveDataObservers() {
        /*
        Observer for the MutableLiveData<User> object. Whenever the User model in the ViewModel
        is updated, this observer is called. Subsequently, the only changes to the User object
        are when the user successfully logs in, so the two tracks of this observer are to
        either: log in as a guest, or log in as a real user.
         */
        mViewModel.getUserLiveData().observe(getViewLifecycleOwner(), user -> {
            if (user.getId() == 0) {
                onSuccessfulLogin(new GuestUser());
            } else {
                onSuccessfulLogin(user);
            }
        });

        /*
        Observer for the MutableLiveData<LoginCloudResponse> object. Whenever the
        LoginCloudResponse object is updated by the ViewModel, this observer will be called. This
        change will affect the UI as a result.
         */
        mViewModel.getResponseLiveData().observe(getViewLifecycleOwner(), loginResponse -> {
            switch (loginResponse) {
                case PASSWORD_INCORRECT:
                    break;
                case NO_INTERNET:
                    break;
                case SEARCHING:
                    callBackListener.showLoadingIcon();
                    break;
                case NO_USER_FOUND_FOR_EMAIL:
                    callBackListener.hideLoadingIcon();
                    break;
                case USER_FOUND:
                    callBackListener.hideLoadingIcon();
                    break;
            }
        });

        /*
         Observer for the MutableLiveData<LoginFormState> object. Whenever the LoginFormState
         object is modified, this observer will be called, and the change will affect the UI.
         */
        mViewModel.getLoginFormStateLiveData().observe(getViewLifecycleOwner(), loginFormState -> {
            if (loginFormState == null) {
                return;
            }

            mLoginButton.setEnabled(loginFormState.isDataValid());
            if (loginFormState.getEmailError() != null) {
                mEmailEditText.setError(getString(loginFormState.getEmailError()));
            }
            if (loginFormState.getPasswordError() != null) {
                mPasswordEditText.setError(getString(loginFormState.getPasswordError()));
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
                mViewModel.onUpdateLoginInfo(
                        mEmailEditText.getText().toString(),
                        mPasswordEditText.getText().toString());
            }
        };

        mEmailEditText.addTextChangedListener(textWatcher);
        mPasswordEditText.addTextChangedListener(textWatcher);
    }

    private void createButtonListeners() {
        /*
        This listener is called when the user clicks the "Log in" button. The email and password
        are grabbed from the EditTexts and are subsequently passed to the ViewModel to log the
        user in.
         */
        mLoginButton.setOnClickListener(v -> {
            callBackListener.hideKeyboard();

            String username = mEmailEditText.getText().toString();
            String password = mPasswordEditText.getText().toString();

            mViewModel.login(username, password);
        });

        /*
        This listener will be called when the "Create an Account" button is clicked. The user
        will be redirected to a the RegisterActivity, in which they can create a new user account
        to login to the app.
         */
        mCreateAccountBtn.setOnClickListener(v -> {
            RegisterFragment fragment = RegisterFragment.newInstance();

            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.user_login_content_fragment, fragment)
                    .addToBackStack(null)
                    .commit();
        });

        /*
        This listener will be called when the "Forgot your Password" button is clicked. The user
        will be redirected to the ForgotPasswordActivity afterwards, in which they will be
        prompted to enter their email address in order to renew their password.
         */
        mForgotPasswordBtn.setOnClickListener(v -> {
            ForgotPasswordEmailFragment fragment = ForgotPasswordEmailFragment.newInstance();

            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.user_login_content_fragment, fragment)
                    .addToBackStack(null)
                    .commit();
        });
    }

    private void onSuccessfulLogin(User user) {
        Toast.makeText(getActivity(), String.format("Welcome, %s!", user.getFullName()),
                Toast.LENGTH_LONG).show();

        Intent intent = new Intent(requireActivity(), HomeActivity.class);
        intent.putExtra("id", user.getId());

        startActivity(intent);
    }

    private void onUnsuccessfulLogin() {
        Toast.makeText(requireActivity().getApplicationContext(), "Incorrect login",
                Toast.LENGTH_SHORT).show();
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
}