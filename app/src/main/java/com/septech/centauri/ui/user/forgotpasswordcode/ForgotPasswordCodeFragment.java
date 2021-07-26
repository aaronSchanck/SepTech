package com.septech.centauri.ui.user.forgotpasswordcode;

import android.content.Context;
import android.os.Bundle;
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
import com.septech.centauri.ui.user.forgotpassword.ForgotPasswordViewModel;
import com.septech.centauri.ui.interfaces.CallBackListener;
import com.septech.centauri.ui.user.forgotpasswordnew.ForgotPasswordNewFragment;

public class ForgotPasswordCodeFragment extends Fragment {

    private ForgotPasswordCodeViewModel mViewModel;
    private ForgotPasswordViewModel mForgotPasswordViewModel;

    private CallBackListener callBackListener;

    private EditText mCodeEditText;

    private Button mVerifyButton;

    public static ForgotPasswordCodeFragment newInstance() {
        return new ForgotPasswordCodeFragment();
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
        View view = inflater.inflate(R.layout.user_forgot_password_code_fragment, container,
                false);

        mVerifyButton = view.findViewById(R.id.user_forgot_password_code_verify_btn);

        mCodeEditText = view.findViewById(R.id.user_forgot_password_code_edittext);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ForgotPasswordCodeViewModel.class);
        mForgotPasswordViewModel =
                new ViewModelProvider(requireActivity()).get(ForgotPasswordViewModel.class);

        mVerifyButton.setOnClickListener(v -> {
            callBackListener.hideKeyboard();
            mViewModel.verifyCodeSubmit(mCodeEditText.getText().toString(),
                    mForgotPasswordViewModel.getEmailLiveData().getValue());
        });

        mViewModel.getFormState().observe(getViewLifecycleOwner(), forgotPasswordCodeFormState -> {
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

        mViewModel.getResponseLiveData().observe(getViewLifecycleOwner(), forgotPasswordCodeCloudResponse -> {
            int duration = Toast.LENGTH_SHORT;

            CharSequence text;
            switch (forgotPasswordCodeCloudResponse) {
                case NO_CODE:
                    text = "Incorrect code";
                    Toast.makeText(getActivity(), text, duration).show();
                    break;
                case NO_INTERNET:
                    text = "No internet connection";
                    Toast.makeText(getActivity(), text, duration).show();
                    break;
                case LOADING:
                    text = "Connecting to server...";
                    Toast.makeText(getActivity(), text, duration).show();
                    break;
                case CODE_MATCHED:
                    text = "Code matched";
                    Toast.makeText(getActivity(), text, duration).show();

                    ForgotPasswordNewFragment fragment = ForgotPasswordNewFragment.newInstance();

                    requireActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.user_login_content_fragment, fragment)
                            .addToBackStack(null)
                            .commit();
                    break;
            }
        });
    }

}