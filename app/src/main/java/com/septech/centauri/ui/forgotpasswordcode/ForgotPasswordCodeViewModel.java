package com.septech.centauri.ui.forgotpasswordcode;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.septech.centauri.data.model.user.UserEntity;
import com.septech.centauri.data.repository.UserDataRepository;
import com.septech.centauri.domain.repository.UserRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ForgotPasswordCodeViewModel extends ViewModel {
    private UserRepository userRepo;
    private CompositeDisposable mDisposables = new CompositeDisposable();

    private MutableLiveData<ForgotPasswordCodeFormState> formLiveData =
            new MutableLiveData<>();
    private MutableLiveData<ForgotPasswordCodeCloudResponse> responseLiveData = new MutableLiveData<>();


    public ForgotPasswordCodeViewModel() {
        this.userRepo = UserDataRepository.getInstance();

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

    public void verifyCodeSubmit(String code) {
        mDisposables.add(userRepo.getPasswordResetCode(code)     // TODO: change this, should not need to get the user again
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<UserEntity>() {
                    @Override
                    public void onStart() {
                        responseLiveData.setValue(ForgotPasswordCodeCloudResponse.LOADING);
                    }

                    @Override
                    public void onSuccess(@NonNull UserEntity o) {
                        responseLiveData.setValue(ForgotPasswordCodeCloudResponse.CODE_FOUND);
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
