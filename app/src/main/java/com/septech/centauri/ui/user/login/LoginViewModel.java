package com.septech.centauri.ui.user.login;

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


public class LoginViewModel extends ViewModel {
    private static final String TAG = "LoginViewModel";

    private final int MAX_LOGIN_TRIES = 5;

    private int loginTries;

    private final UserRepository userRepo;

    private final CompositeDisposable mDisposables = new CompositeDisposable();

    private final MutableLiveData<User> userLiveData = new MutableLiveData<>();
    private final MutableLiveData<LoginResponse> responseLiveData = new MutableLiveData<>();
    private final MutableLiveData<LoginFormState> loginFormStateLiveData = new MutableLiveData<>();

    public LoginViewModel() {
        this.userRepo = UserDataRepository.getInstance();
        this.loginTries = 0;
    }

    public MutableLiveData<User> getUserLiveData() {
        return userLiveData;
    }

    public MutableLiveData<LoginResponse> getResponseLiveData() {
        return responseLiveData;
    }

    public MutableLiveData<LoginFormState> getLoginFormStateLiveData() {
        return loginFormStateLiveData;
    }

    @Override
    protected void onCleared() {
        mDisposables.clear();
    }

    public void login(String email, String password) {
        mDisposables.add(userRepo.getUserByEmail(email)
                .flatMap(user -> userRepo.login(email, password, user.getPasswordSalt()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<User>() {
                    @Override
                    protected void onStart() {
                        responseLiveData.setValue(LoginResponse.SEARCHING);
                    }

                    @Override
                    public void onSuccess(@NonNull User user) {
                        responseLiveData.setValue(LoginResponse.USER_FOUND);
                        userLiveData.setValue(user);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        responseLiveData.setValue(LoginResponse.PASSWORD_INCORRECT);
                        loginTries += 1;
                    }
                }));
    }

    public void onUpdateUsername(String username) {
        //TODO
    }

    public void onUpdatePassword(String password) {
        //TODO
    }

    public void onUpdateLoginInfo(String username, String password) {
        if (!usernameIsValid(username))
            loginFormStateLiveData.setValue(new LoginFormState(R.string.string_username_incorrect,
                    null));
        else if (!passwordIsValid(password))
            loginFormStateLiveData.setValue(new LoginFormState(null,
                    R.string.string_password_incorrect));
        else
            loginFormStateLiveData.setValue(new LoginFormState(true));
    }

    private boolean usernameIsValid(String username) {
        return username.length() > 5;
    }

    private boolean passwordIsValid(String password) {
        return password.length() > 3;
    }
}
