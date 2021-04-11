package com.septech.centauri.ui.user.newpassword;

import android.util.Log;
import android.util.Patterns;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.septech.centauri.R;
import com.septech.centauri.data.repository.UserDataRepository;
import com.septech.centauri.data.utils.PasswordUtils;
import com.septech.centauri.data.utils.PasswordValidator;
import com.septech.centauri.domain.models.User;
import com.septech.centauri.domain.repository.UserRepository;
import com.septech.centauri.lib.DateTime;
import com.septech.centauri.ui.user.register.RegisterFormState;
import com.septech.centauri.ui.user.register.RegisterResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class NewPasswordViewModel extends ViewModel {
    private static final String TAG = "NewPasswordViewModel";

    private final MutableLiveData<NewPasswordFormState> mNewPasswordFormState = new MutableLiveData<>();
    private final MutableLiveData<NewPasswordCloudResponse> responseLiveData = new MutableLiveData<>();

    private final UserRepository userRepo;

    private final CompositeDisposable mDisposables = new CompositeDisposable();

    public NewPasswordViewModel() {
        userRepo = UserDataRepository.getInstance();

        mNewPasswordFormState.setValue(new NewPasswordFormState());
    }

    public MutableLiveData<NewPasswordCloudResponse> getResponseLiveData() {
        return responseLiveData;
    }

    @Override
    protected void onCleared() {
        mDisposables.clear();
    }

    public void changePassword(String password, String email) {
        PasswordUtils pwUtils = new PasswordUtils(password);
        String pwHash = pwUtils.hash();

        User user = new User();

        user.setPassword(pwHash);
        user.setPasswordSalt(pwUtils.getSalt());

        mDisposables.add(userRepo.changePassword(user, email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<User>() {
                    @Override
                    public void onStart() {
                        responseLiveData.setValue(NewPasswordCloudResponse.LOADING);
                    }

                    @Override
                    public void onSuccess(@NonNull User user) {
                        responseLiveData.setValue(NewPasswordCloudResponse.SUCCESS);
                        System.out.println("user = " + user);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        responseLiveData.setValue(NewPasswordCloudResponse.MISMATCH_PASSWORD);
                        System.out.println("e = " + e);
                    }
                })
        );
    }

    public MutableLiveData<NewPasswordFormState> getNewPasswordFormState() {
        return mNewPasswordFormState;
    }


    public void onUpdatePassword(String password, String confirmPassword) {
        NewPasswordFormState newPasswordFormState = mNewPasswordFormState.getValue();

        assert newPasswordFormState != null;
        newPasswordFormState.setPasswordEdited(true);

        PasswordValidator pwVal = new PasswordValidator(password);

        if (!pwVal.isValid()) {
            newPasswordFormState.setPasswordError(pwVal.getPwError());
        } else {
            newPasswordFormState.setPasswordError(pwVal.getPwError());
            newPasswordFormState.checkDataValid();
        }

        if (!isConfirmPasswordValid(confirmPassword, password)) {
            newPasswordFormState.setConfirmPasswordError(R.string.string_confirm_password_incorrect);
        } else {
            newPasswordFormState.setConfirmPasswordError(null);
            newPasswordFormState.checkDataValid();
        }

        mNewPasswordFormState.setValue(newPasswordFormState);
    }

    public void onUpdateConfirmPassword(String confirmPassword, String password) {
        NewPasswordFormState newPasswordFormState = mNewPasswordFormState.getValue();

        assert newPasswordFormState != null;
        newPasswordFormState.setConfirmPasswordEdited(true);

        if (!isConfirmPasswordValid(confirmPassword, password)) {
            newPasswordFormState.setConfirmPasswordError(R.string.string_confirm_password_incorrect);
        } else {
            newPasswordFormState.setConfirmPasswordError(null);
            newPasswordFormState.checkDataValid();
        }

        mNewPasswordFormState.setValue(newPasswordFormState);
    }

    private boolean isConfirmPasswordValid(String confirmPassword, String password) {
        return confirmPassword.equals(password);
    }
}