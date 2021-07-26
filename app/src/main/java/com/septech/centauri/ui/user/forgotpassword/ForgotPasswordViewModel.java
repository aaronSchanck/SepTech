package com.septech.centauri.ui.user.forgotpassword;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.septech.centauri.data.repository.UserRepositoryImpl;
import com.septech.centauri.domain.models.User;
import com.septech.centauri.domain.repository.UserRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ForgotPasswordViewModel extends ViewModel {
    private final UserRepository userRepo;
    private final CompositeDisposable mDisposables;

    private MutableLiveData<String> emailLiveData;

    public ForgotPasswordViewModel() {
        this.userRepo = UserRepositoryImpl.getInstance();
        mDisposables = new CompositeDisposable();
    }

    @Override
    protected void onCleared() {
        mDisposables.clear();
    }

    public MutableLiveData<String> getEmailLiveData() {
        if(emailLiveData == null) {
            emailLiveData = new MutableLiveData<>();
        }
        return emailLiveData;
    }

    public void setEmailLiveData(String email) {
        if(emailLiveData == null) {
            emailLiveData = new MutableLiveData<>();
        }

        this.emailLiveData.setValue(email);
    }
}
