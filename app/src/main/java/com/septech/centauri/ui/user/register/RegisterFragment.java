package com.septech.centauri.ui.user.register;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.septech.centauri.R;
import com.septech.centauri.ui.interfaces.CallBackListener;

public class RegisterFragment extends Fragment {

    private RegisterViewModel mViewModel;

    private CallBackListener callBackListener;

    private EditText mFNameEditText;
    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private EditText mConfirmPasswordEditText;
    private EditText mPhoneNumberEditText;

    private DatePicker mDatePicker;

    private Button mCreateAccountBtn;

    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            callBackListener = (CallBackListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement CallBackListener");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_register_fragment, container, false);

        mFNameEditText = view.findViewById(R.id.user_register_fullname_edittext);
        mEmailEditText = view.findViewById(R.id.user_register_email_edittext);
        mPasswordEditText = view.findViewById(R.id.user_register_password_edittext);
        mConfirmPasswordEditText = view.findViewById(R.id.user_register_confirmpassword_edittext);
        mPhoneNumberEditText = view.findViewById(R.id.user_register_phonenumber_edittext);

        createTextWatchers();

        mEmailEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                mViewModel.checkUserExists(mEmailEditText.getText().toString());
            }
        });

        mDatePicker = view.findViewById(R.id.dob_input);

        mCreateAccountBtn = view.findViewById(R.id.business_register_create_account_btn);

        mCreateAccountBtn.setOnClickListener(v -> mViewModel.createAccount(
                mEmailEditText.getText().toString(),
                mPasswordEditText.getText().toString(),
                mFNameEditText.getText().toString(),
                mPhoneNumberEditText.getText().toString(),
                getDateOfBirth()));

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        mViewModel.getRegisterFormState().observe(getViewLifecycleOwner(), registerFormState -> {
            if (registerFormState == null) {
                return;
            }

            mCreateAccountBtn.setEnabled(true);

            if (registerFormState.isFullNameEdited() && registerFormState.getFullNameError() != null) {
                mFNameEditText.setError(getString(registerFormState.getFullNameError()));
            } else {
                mFNameEditText.setError(null);
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
        });

        mViewModel.getResponseLiveData().observe(getViewLifecycleOwner(), response -> {
            switch (response) {
                case EMAIL_EXISTS:
                    mEmailEditText.setError("Email already exists");
                    break;
                case EMAIL_DOES_NOT_EXIST:
                    mEmailEditText.setError(null);
                    break;
                case INFO_INCORRECT:
                    callBackListener.hideLoadingIcon();
                    break;
                case LOADING:
                    callBackListener.showLoadingIcon();
                    break;
                case SUCCESS:
                    callBackListener.hideLoadingIcon();
                    break;
            }
        });
    }

    private void createTextWatchers() {
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
                mViewModel.onUpdateFullName(mFNameEditText.getText().toString());
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
                mViewModel.onUpdateEmail(mEmailEditText.getText().toString());
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
                mViewModel.onUpdatePassword(mPasswordEditText.getText().toString(),
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
                mViewModel.onUpdateConfirmPassword(mConfirmPasswordEditText.getText().toString(), mPasswordEditText.getText().toString());
            }
        });

        mPhoneNumberEditText.addTextChangedListener(new PhoneNumberFormattingTextWatcher("1"));
    }

    private String getDateOfBirth() {
        int day = mDatePicker.getDayOfMonth();
        int month = (mDatePicker.getMonth() + 1);
        int year = mDatePicker.getYear();

        @SuppressLint("DefaultLocale") String dob = String.format("%d-%d-%d", year, month, day);

        return dob;
    }
}