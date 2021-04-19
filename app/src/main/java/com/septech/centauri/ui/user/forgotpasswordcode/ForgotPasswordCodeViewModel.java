package com.septech.centauri.ui.user.forgotpasswordcode;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.septech.centauri.data.repository.UserRepositoryImpl;
import com.septech.centauri.domain.repository.UserRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ForgotPasswordCodeViewModel extends ViewModel {
    private static final String TAG = "ForgotPasswordCode_VM";

    private UserRepository userRepo;
    private CompositeDisposable mDisposables = new CompositeDisposable();

    private MutableLiveData<ForgotPasswordCodeFormState> formLiveData =
            new MutableLiveData<>();
    private MutableLiveData<ForgotPasswordCodeCloudResponse> responseLiveData = new MutableLiveData<>();


    public ForgotPasswordCodeViewModel() {
        this.userRepo = UserRepositoryImpl.getInstance();

        formLiveData.setValue(new ForgotPasswordCodeFormState());
    }

    @Override
    protected void onCleared() {
        mDisposables.clear();
    }

    public MutableLiveData<ForgotPasswordCodeFormState> getFormLiveData() {
        return formLiveData;
    }

    public MutableLiveData<ForgotPasswordCodeCloudResponse> getResponseLiveData() {
        return responseLiveData;
    }

    public void verifyCodeSubmit(String code, String email) {
        mDisposables.add(userRepo.verifyPasswordCode(code, email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<String>() {
                    @Override
                    public void onStart() {
                        responseLiveData.setValue(ForgotPasswordCodeCloudResponse.LOADING);
                    }

                    @Override
                    public void onSuccess(@NonNull String o) {
                        Log.d(TAG, code);
                        responseLiveData.setValue(ForgotPasswordCodeCloudResponse.CODE_MATCHED);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        //check for error, declare NO_INTERNET or NO_CODE accordingly
                        responseLiveData.setValue(ForgotPasswordCodeCloudResponse.NO_CODE);
                    }

                })
        );
    }
}