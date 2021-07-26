package com.septech.centauri.ui.user.forgotpasswordnew;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.septech.centauri.R;
import com.septech.centauri.data.repository.UserRepositoryImpl;
import com.septech.centauri.data.utils.PasswordUtils;
import com.septech.centauri.data.utils.PasswordValidator;
import com.septech.centauri.domain.models.User;
import com.septech.centauri.domain.repository.UserRepository;

import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ForgotPasswordNewViewModel extends ViewModel {
    private static final String TAG = "NewPasswordViewModel";

    private final UserRepository userRepo;
    private final CompositeDisposable mDisposables;

    private MutableLiveData<ForgotPasswordNewCloudResponse> responseLiveData;
    private MutableLiveData<ForgotPasswordNewFormState> formState;

    public ForgotPasswordNewViewModel() {
        userRepo = UserRepositoryImpl.getInstance();
        mDisposables = new CompositeDisposable();
    }

    public MutableLiveData<ForgotPasswordNewCloudResponse> getResponseLiveData() {
        if (responseLiveData == null) {
            responseLiveData = new MutableLiveData<>();
        }
        return responseLiveData;
    }

    @Override
    protected void onCleared() {
        mDisposables.clear();
    }

    public void changePassword(String email, String newPassword) {



        mDisposables.add(userRepo.getUserByEmail(email)
                .flatMap(user -> {
                    PasswordUtils pwUtils = new PasswordUtils(newPassword);
                    String pwHash = pwUtils.hash();

                    user.setPassword(pwHash);
                    user.setPasswordSalt(pwUtils.getSalt());

                    return userRepo.update(user.getId(), user);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<User>() {
                    @Override
                    public void onStart() {
                        responseLiveData.setValue(ForgotPasswordNewCloudResponse.LOADING);
                    }

                    @Override
                    public void onSuccess(@NonNull User user) {
                        responseLiveData.setValue(ForgotPasswordNewCloudResponse.SUCCESS);
                        Log.d("POST ", user.getPasswordSalt());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        responseLiveData.setValue(ForgotPasswordNewCloudResponse.MISMATCH_PASSWORD);
                        System.out.println("e = " + e);
                    }
                })
        );
    }

    public MutableLiveData<ForgotPasswordNewFormState> getFormState() {
        if (formState == null) {
            formState = new MutableLiveData<>();
            formState.setValue(new ForgotPasswordNewFormState());
        }
        return formState;
    }

    public void onUpdatePassword(String password, String confirmPassword) {
        ForgotPasswordNewFormState formState = this.formState.getValue();

        assert formState != null;
        formState.setPasswordEdited(true);

        PasswordValidator pwVal = new PasswordValidator(password);

        if (!pwVal.isValid()) {
            formState.setPasswordError(pwVal.getPwError());
        } else {
            formState.setPasswordError(pwVal.getPwError());
            formState.checkDataValid();
        }

        if (!isConfirmPasswordValid(confirmPassword, password)) {
            formState.setConfirmPasswordError(R.string.string_confirm_password_incorrect);
        } else {
            formState.setConfirmPasswordError(null);
            formState.checkDataValid();
        }

        this.formState.setValue(formState);
    }

    public void onUpdateConfirmPassword(String confirmPassword, String password) {
        ForgotPasswordNewFormState forgotPasswordNewFormState = formState.getValue();

        assert forgotPasswordNewFormState != null;
        forgotPasswordNewFormState.setConfirmPasswordEdited(true);

        if (!isConfirmPasswordValid(confirmPassword, password)) {
            forgotPasswordNewFormState.setConfirmPasswordError(R.string.string_confirm_password_incorrect);
        } else {
            forgotPasswordNewFormState.setConfirmPasswordError(null);
            forgotPasswordNewFormState.checkDataValid();
        }

        formState.setValue(forgotPasswordNewFormState);
    }

    private boolean isConfirmPasswordValid(String confirmPassword, String password) {
        return confirmPassword.equals(password);
    }
}