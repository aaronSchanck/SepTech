package com.septech.centauri.ui.user.register;

import android.util.Log;
import android.util.Patterns;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.septech.centauri.R;
import com.septech.centauri.data.repository.UserRepositoryImpl;
import com.septech.centauri.data.utils.PasswordUtils;
import com.septech.centauri.data.utils.PasswordValidator;
import com.septech.centauri.domain.models.User;
import com.septech.centauri.domain.repository.UserRepository;
import com.septech.centauri.lib.DateTime;
import com.septech.centauri.ui.chat.ChatConnection;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class RegisterViewModel extends ViewModel {
    private static final String TAG = "RegisterViewModel";

    private MutableLiveData<RegisterFormState> formState;
    private MutableLiveData<RegisterResponse> responseLiveData;

    private final UserRepository userRepo;

    private final CompositeDisposable mDisposables;

    public RegisterViewModel() {
        userRepo = UserRepositoryImpl.getInstance();
        mDisposables = new CompositeDisposable();
    }
    
    @Override
    protected void onCleared() {
        mDisposables.clear();
    }

    public void createAccount(String email, String password, String fullName, String phoneNumber, String dob) {
        PasswordUtils pwUtils = new PasswordUtils(password);
        String pwHash = pwUtils.hash();

        User user = new User(email, pwHash, pwUtils.getSalt(), fullName, dob, phoneNumber);

        mDisposables.add(userRepo.createAccount(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<User>() {
                    @Override
                    public void onStart() {
                        responseLiveData.setValue(RegisterResponse.LOADING);
                    }

                    @Override
                    public void onSuccess(@NonNull User user) {
                        responseLiveData.setValue(RegisterResponse.SUCCESS);
                        System.out.println("user = " + user);

/**
                        // Register user on the chat server
                        String jid = user.getEmail().replaceAll("[@.]", "") + "@chat.septech.me";

                        Log.d(TAG, "jid: " + jid);
                        com.septech.centauri.domain.chat.models.User newUser = new com.septech.centauri.domain.chat.models.User(
                                "jid",
                                user.getFullName(),
                                null);

                        Map<String, String> attributes = new HashMap<String, String>() {{
                            put("name", user.getFullName());
                            put("email", user.getEmail());
                            put("date", user.getCreatedAt());
                            put("dob", user.getDateOfBirth());
                        }};

                        try {
                            ChatConnection.register(jid, user.getPassword(), attributes, newUser);
                        } catch (IOException | XMPPException | SmackException | InterruptedException e) {
                            e.printStackTrace();
                        }
 **/
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        responseLiveData.setValue(RegisterResponse.INFO_INCORRECT);
                        System.out.println("e = " + e);
                    }
                })
        );
    }

    public void checkUserExists(String email) {
        mDisposables.add(userRepo.checkUserExists(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<String>() {
                    @Override
                    public void onSuccess(@NonNull String string) {
                        responseLiveData.setValue(RegisterResponse.EMAIL_DOES_NOT_EXIST);
                        System.out.println("user = " + string);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        responseLiveData.setValue(RegisterResponse.EMAIL_EXISTS);
                        System.out.println("e = " + e);
                    }
                })
        );
    }

    public MutableLiveData<RegisterFormState> getRegisterFormState() {
        if(formState == null) {
            formState = new MutableLiveData<>();
            formState.setValue(new RegisterFormState());
        }
        return formState;
    }

    public MutableLiveData<RegisterResponse> getResponseLiveData() {
        if(responseLiveData == null) {
            responseLiveData = new MutableLiveData<>();
        }
        return responseLiveData;
    }

    public void onUpdateFullName(String fname) {
        RegisterFormState registerFormState = formState.getValue();

        assert registerFormState != null;
        registerFormState.setFullNameEdited(true);

        if (!isFullNameValid(fname)) {
            registerFormState.setFullNameError(R.string.register_string_fname_incorrect);
        } else {
            registerFormState.setFullNameError(null);
            registerFormState.checkDataValid();
        }

        formState.setValue(registerFormState);
    }

    public void onUpdateEmail(String email) {
        RegisterFormState registerFormState = formState.getValue();

        assert registerFormState != null;
        registerFormState.setEmailEdited(true);

        if (!isEmailValid(email)) {
            registerFormState.setEmailError(R.string.register_string_email_incorrect);
        } else {
            registerFormState.setEmailError(null);
            registerFormState.checkDataValid();
        }

        formState.setValue(registerFormState);
    }

    public void onUpdatePassword(String password, String confirmPassword) {
        RegisterFormState registerFormState = formState.getValue();

        assert registerFormState != null;
        registerFormState.setPasswordEdited(true);

        PasswordValidator pwVal = new PasswordValidator(password);

        if (!pwVal.isValid()) {
            registerFormState.setPasswordError(pwVal.getPwError());
        } else {
            registerFormState.setPasswordError(pwVal.getPwError());
            registerFormState.checkDataValid();
        }

        if (!isConfirmPasswordValid(confirmPassword, password)) {
            registerFormState.setConfirmPasswordError(R.string.register_string_confirm_password_incorrect);
        } else {
            registerFormState.setConfirmPasswordError(null);
            registerFormState.checkDataValid();
        }

        formState.setValue(registerFormState);
    }

    public void onUpdateConfirmPassword(String confirmPassword, String password) {
        RegisterFormState registerFormState = formState.getValue();

        assert registerFormState != null;
        registerFormState.setConfirmPasswordEdited(true);

        if (!isConfirmPasswordValid(confirmPassword, password)) {
            registerFormState.setConfirmPasswordError(R.string.register_string_confirm_password_incorrect);
        } else {
            registerFormState.setConfirmPasswordError(null);
            registerFormState.checkDataValid();
        }

        formState.setValue(registerFormState);
    }

    public boolean isFullNameValid(String fullName) {
        return fullName.length() > 1;
    }

    public boolean isEmailValid(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isConfirmPasswordValid(String confirmPassword, String password) {
        return confirmPassword.equals(password);
    }
}
