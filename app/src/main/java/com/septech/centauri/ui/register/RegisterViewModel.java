package com.septech.centauri.ui.register;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.septech.centauri.R;
import com.septech.centauri.data.repository.UserDataRepository;
import com.septech.centauri.domain.models.User;
import com.septech.centauri.domain.repository.UserRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class RegisterViewModel extends ViewModel {
    private static final String TAG = "RegisterViewModel";

    private final MutableLiveData<RegisterFormState> mRegisterFormState = new MutableLiveData<>();
    private final MutableLiveData<RegisterCloudResponse> responseLiveData = new MutableLiveData<>();

    private final UserRepository userRepo;

    private CompositeDisposable mDisposables = new CompositeDisposable();

    public RegisterViewModel() {
        userRepo = UserDataRepository.getInstance();

        mRegisterFormState.setValue(new RegisterFormState());
    }

    public MutableLiveData<RegisterCloudResponse> getResponseLiveData() {
        return responseLiveData;
    }


    @Override
    protected void onCleared() {
        mDisposables.clear();
    }

    public void createAccount(String email, String password, String fullName, String phoneNumber) {
        mDisposables.add(userRepo.createAccount(email, password, fullName, phoneNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<User>() {
                    @Override
                    public void onSuccess(@NonNull User user) {
                        System.out.println("user = " + user);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        System.out.println("e = " + e);
                    }
                })
        );
    }

    public MutableLiveData<RegisterFormState> getRegisterFormState() {
        return mRegisterFormState;
    }

    public void onUpdateFullName(String fname) {
        RegisterFormState registerFormState = mRegisterFormState.getValue();

        assert registerFormState != null;
        registerFormState.setFullNameEdited(true);

        if (!isFullNameValid(fname)) {
            registerFormState.setFullNameError(R.string.register_string_fname_incorrect);
        } else {
            registerFormState.setFullNameError(null);
            registerFormState.checkDataValid();
        }

        mRegisterFormState.setValue(registerFormState);
    }

//    public void onUpdateLastName(String lname) {
//        RegisterFormState registerFormState = mRegisterFormState.getValue();
//
//        assert registerFormState != null;
//        registerFormState.setLastNameEdited(true);
//
//        if (!isLastNameValid(lname)) {
//            registerFormState.setLastNameError(R.string.register_string_lname_incorrect);
//        } else {
//            registerFormState.setLastNameError(null);
//            registerFormState.checkDataValid();
//        }
//
//        mRegisterFormState.setValue(registerFormState);
//    }

    public void onUpdateEmail(String email) {
        RegisterFormState registerFormState = mRegisterFormState.getValue();

        assert registerFormState != null;
        registerFormState.setEmailEdited(true);

        if (!isEmailValid(email)) {
            registerFormState.setEmailError(R.string.register_string_email_incorrect);
        } else {
            registerFormState.setEmailError(null);
            registerFormState.checkDataValid();
        }

        mRegisterFormState.setValue(registerFormState);
    }

    public void onUpdatePassword(String password, String confirmPassword) {
        RegisterFormState registerFormState = mRegisterFormState.getValue();

        assert registerFormState != null;
        registerFormState.setPasswordEdited(true);

        if (!isPasswordValid(password)) {
            registerFormState.setPasswordError(R.string.register_string_password_incorrect);
        } else {
            registerFormState.setPasswordError(null);
            registerFormState.checkDataValid();
        }

        if (!isConfirmPasswordValid(confirmPassword, password)) {
            registerFormState.setConfirmPasswordError(R.string.register_string_confirm_password_incorrect);
        } else {
            registerFormState.setConfirmPasswordError(null);
            registerFormState.checkDataValid();
        }

        mRegisterFormState.setValue(registerFormState);
    }

    public void onUpdateConfirmPassword(String confirmPassword, String password) {
        RegisterFormState registerFormState = mRegisterFormState.getValue();

        assert registerFormState != null;
        registerFormState.setConfirmPasswordEdited(true);

        if (!isConfirmPasswordValid(confirmPassword, password)) {
            registerFormState.setConfirmPasswordError(R.string.register_string_confirm_password_incorrect);
        } else {
            registerFormState.setConfirmPasswordError(null);
            registerFormState.checkDataValid();
        }

        mRegisterFormState.setValue(registerFormState);
    }

    public void onUpdatePhoneNumber(String phoneNumber) {
        RegisterFormState registerFormState = mRegisterFormState.getValue();

        assert registerFormState != null;
        registerFormState.setPhoneNumberEdited(true);

        if (!isPhoneNumberValid(phoneNumber)) {
            registerFormState.setPhoneNumberError(R.string.register_string_phone_number_incorrect);
        } else {
            registerFormState.setPhoneNumberError(null);
            registerFormState.checkDataValid();
        }

        mRegisterFormState.setValue(registerFormState);
    }

    public boolean isFullNameValid(String fullName) {
        return fullName.length() > 1;
    }

//    public boolean isLastNameValid(String lastName) {
//        return lastName.length() > 1;
//    }

    public boolean isEmailValid(String email) {
        return false;
    }

    public boolean isPasswordValid(String password) {
        return false;
    }

    private boolean isConfirmPasswordValid(String confirmPassword, String password) {
        return confirmPassword.equals(password);
    }

    public boolean isPhoneNumberValid(String phoneNumber) {
        return false;
    }
}