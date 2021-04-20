package com.septech.centauri.ui.user.forgotpassword;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.septech.centauri.R;
import com.septech.centauri.ui.user.forgotpasswordcode.ForgotPasswordCodeFragment;

public class ForgotPasswordEmailFragment extends Fragment {
    private ForgotPasswordEmailViewModel mViewModel;
    private ForgotPasswordViewModel mForgotPasswordViewModel;

    private EditText mEmailEditText;

    private Button mSubmitButton;

    public static ForgotPasswordEmailFragment newInstance() {
        return new ForgotPasswordEmailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_forgot_password_fragment, container, false);

        mSubmitButton = view.findViewById(R.id.forgot_password_btn);

        mEmailEditText = view.findViewById(R.id.forgot_password_email_edittext);


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ForgotPasswordEmailViewModel.class);
        mForgotPasswordViewModel = new ViewModelProvider(this).get(ForgotPasswordViewModel.class);

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
            mViewModel.forgotPasswordSubmit(mEmailEditText.getText().toString());
        });

        mViewModel.getUserLiveData().observe(getViewLifecycleOwner(), user -> {
            mForgotPasswordViewModel.getUserLiveData().setValue(user);

            ForgotPasswordCodeFragment fragment =
                    ForgotPasswordCodeFragment.newInstance();

            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.user_login_content_fragment, fragment)
                    .addToBackStack(null)
                    .commit();
        });

        mViewModel.getFormLiveData().observe(getViewLifecycleOwner(), forgotPasswordFormState -> {
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

        mViewModel.getResponseLiveData().observe(getViewLifecycleOwner(),
                forgotPasswordCloudResponse -> {
                    int duration = Toast.LENGTH_SHORT;

                    CharSequence text;
                    Toast toast;
                    switch (forgotPasswordCloudResponse) {
                        case NO_USER_FOUND:
                            text = "No user found with associated email";
                            toast = Toast.makeText(getActivity(), text, duration);
                            toast.show();
                            break;
                        case NO_INTERNET:
                            text = "No internet connection";
                            toast = Toast.makeText(getActivity(), text, duration);
                            toast.show();
                            break;
                        case LOADING:
                            text = "Connecting to server...";
                            toast = Toast.makeText(getActivity(), text, duration);
                            toast.show();
                            break;
                        case USER_FOUND:
                            text = "User found!";
                            toast = Toast.makeText(getActivity(), text, duration);
                            toast.show();

                            break;
                    }
                });
    }
}