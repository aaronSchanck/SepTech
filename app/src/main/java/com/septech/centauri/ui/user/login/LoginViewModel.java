package com.septech.centauri.ui.user.login;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.septech.centauri.R;
import com.septech.centauri.data.repository.UserRepositoryImpl;
import com.septech.centauri.domain.models.User;
import com.septech.centauri.domain.repository.UserRepository;
import com.septech.centauri.ui.chat.ChatLogin;

import java.net.SocketTimeoutException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;


public class LoginViewModel extends AndroidViewModel {
    private static final String TAG = "LoginViewModel";

    private final int MAX_LOGIN_TRIES = 5;

    private Application context;

    private int loginTries;

    private final UserRepository userRepo;

    private final CompositeDisposable mDisposables = new CompositeDisposable();

    private final MutableLiveData<User> userLiveData = new MutableLiveData<>();
    private final MutableLiveData<LoginResponse> responseLiveData = new MutableLiveData<>();
    private final MutableLiveData<LoginFormState> loginFormStateLiveData = new MutableLiveData<>();

    public LoginViewModel(Application context) {
        super(context);
        this.userRepo = UserRepositoryImpl.getInstance();
        this.loginTries = 0;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mDisposables.clear();
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

                        // Login to chat server
                        String jid = user.getEmail().replaceAll("[@.]", "") + "@chat.septech.me";
                        ChatLogin login = new ChatLogin("test_user@chat.septech.me",
                                user.getFullName(),"test", (Context) getApplication());
                        login.saveCredentialsAndLogin();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        if(e instanceof NullPointerException) {
                            responseLiveData.setValue(LoginResponse.NO_USER_FOUND_FOR_EMAIL);
                            loginTries += 1;
                        } else if(e instanceof HttpException) {
                            responseLiveData.setValue(LoginResponse.PASSWORD_INCORRECT);
                            loginTries += 1;
                        } else if(e instanceof SocketTimeoutException) {
                            responseLiveData.setValue(LoginResponse.NO_INTERNET);
                        }
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
