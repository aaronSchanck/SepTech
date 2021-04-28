package com.septech.centauri.ui.user.forgotpasswordnew;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.septech.centauri.R;
import com.septech.centauri.ui.user.forgotpassword.ForgotPasswordViewModel;
import com.septech.centauri.ui.interfaces.CallBackListener;
import com.septech.centauri.ui.user.login.LoginFragment;

public class ForgotPasswordNewFragment extends Fragment {
    private ForgotPasswordNewViewModel mViewModel;
    private ForgotPasswordViewModel mForgotPasswordViewModel;

    private CallBackListener callBackListener;

    private EditText mNewPasswordEditText;
    private EditText mVerifyNewPasswordEditText;

    private Button mVerifyBtn;

    public static ForgotPasswordNewFragment newInstance() {
        return new ForgotPasswordNewFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            callBackListener = (CallBackListener) context;
            callBackListener.initFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement CallBackListener");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_forgot_password_new_fragment, container, false);

        mNewPasswordEditText = view.findViewById(R.id.user_forgot_password_new_password_edittext);
        mVerifyNewPasswordEditText = view.findViewById(R.id.user_forgot_password_new_verify_edittext);

        mVerifyBtn = view.findViewById(R.id.user_forgot_password_code_verify_btn);

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



        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ForgotPasswordNewViewModel.class);
        mForgotPasswordViewModel = new ViewModelProvider(requireActivity()).get(ForgotPasswordViewModel.class);

        mVerifyBtn.setOnClickListener(v -> {
            callBackListener.hideKeyboard();
            mViewModel.changePassword(mForgotPasswordViewModel.getUserLiveData().getValue(),
                    mNewPasswordEditText.getText().toString());

        });

        mViewModel.getResponseLiveData().observe(getViewLifecycleOwner(), response -> {
            int duration = Toast.LENGTH_SHORT;

            CharSequence text;
            Toast toast;
            switch (response) {     // TODO: include a case for password being the same as previous
                case MISMATCH_PASSWORD:
                    text = "Password does not match";
                    toast = Toast.makeText(getActivity(), text, duration);
                    toast.show();
                case LOADING:
                    break;
                case SUCCESS:
                    LoginFragment fragment = LoginFragment.newInstance();

                    requireActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.user_login_content_fragment, fragment)
                            .addToBackStack(null)
                            .commit();
                    break;
            }
        });
    }

}