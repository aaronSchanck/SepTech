package com.septech.centauri.ui.user.login;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.septech.centauri.data.repository.UserRepositoryImpl;
import com.septech.centauri.domain.models.User;
import com.septech.centauri.domain.repository.UserRepository;

import io.reactivex.disposables.CompositeDisposable;

public class UserLoginViewModel extends ViewModel {
    private static final String TAG = "UserLoginViewModel";

    private final int MAX_LOGIN_TRIES = 5;
    private final UserRepository userRepo;
    private final CompositeDisposable mDisposables = new CompositeDisposable();
    private int loginTries;
    private MutableLiveData<User> userLiveData;

    public UserLoginViewModel() {
        this.userRepo = UserRepositoryImpl.getInstance();
        this.loginTries = 0;
    }

    public MutableLiveData<User> getUserLiveData() {
        if (userLiveData == null) {
            userLiveData = new MutableLiveData<>();
        }
        return userLiveData;
    }

    @Override
    protected void onCleared() {
        mDisposables.clear();
    }

    public void login(String email, String password) {
    }
}
