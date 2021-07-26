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

public class ForgotPasswordEmailViewModel extends ViewModel {
    private final UserRepository userRepo;
    private final CompositeDisposable mDisposables;

    private MutableLiveData<ForgotPasswordEmailFormState> formLiveData;
    private MutableLiveData<ForgotPasswordEmailCloudResponse> responseLiveData;

    public ForgotPasswordEmailViewModel() {
        this.userRepo = UserRepositoryImpl.getInstance();
        mDisposables = new CompositeDisposable();
    }

    @Override
    protected void onCleared() {
        mDisposables.clear();
    }

    public void forgotPasswordSubmit(String email) {
        mDisposables.add(userRepo.forgotPassword(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<String>() {
                    @Override
                    public void onStart() {
                        responseLiveData.setValue(ForgotPasswordEmailCloudResponse.LOADING);
                    }

                    @Override
                    public void onSuccess(@NonNull String string) {
                        System.out.println("string = " + string);
                        responseLiveData.setValue(ForgotPasswordEmailCloudResponse.USER_FOUND);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        //check for error, declare NO_INTERNET or NO_USER_FOUND accordingly
                        responseLiveData.setValue(ForgotPasswordEmailCloudResponse.NO_USER_FOUND);
                    }
                })
        );
    }

    public MutableLiveData<ForgotPasswordEmailFormState> getFormLiveData() {
        if (formLiveData == null) {
            formLiveData = new MutableLiveData<>();
            formLiveData.setValue(new ForgotPasswordEmailFormState());
        }
        return formLiveData;
    }

    public MutableLiveData<ForgotPasswordEmailCloudResponse> getResponseLiveData() {
        if (responseLiveData == null) {
            responseLiveData = new MutableLiveData<>();
        }
        return responseLiveData;
    }
}
