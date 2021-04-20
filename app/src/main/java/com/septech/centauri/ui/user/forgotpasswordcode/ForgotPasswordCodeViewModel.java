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

    private final UserRepository userRepo;
    private final CompositeDisposable mDisposables;

    private MutableLiveData<ForgotPasswordCodeFormState> formState;
    private MutableLiveData<ForgotPasswordCodeCloudResponse> responseLiveData;


    public ForgotPasswordCodeViewModel() {
        this.userRepo = UserRepositoryImpl.getInstance();

        mDisposables = new CompositeDisposable();
    }

    @Override
    protected void onCleared() {
        mDisposables.clear();
    }

    public MutableLiveData<ForgotPasswordCodeFormState> getFormState() {
        if(formState == null) {
            formState = new MutableLiveData<>();
            formState.setValue(new ForgotPasswordCodeFormState());
        }
        return formState;
    }

    public MutableLiveData<ForgotPasswordCodeCloudResponse> getResponseLiveData() {
        if(responseLiveData == null) {
            responseLiveData = new MutableLiveData<>();
        }
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