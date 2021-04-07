package com.septech.centauri.ui.user.forgotpassword;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.septech.centauri.data.repository.UserDataRepository;
import com.septech.centauri.domain.models.User;
import com.septech.centauri.domain.repository.UserRepository;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ForgotPasswordViewModel extends ViewModel {
    private UserRepository userRepo;
    private Single<User> user;
    private CompositeDisposable mDisposables = new CompositeDisposable();

    private MutableLiveData<ForgotPasswordFormState> formLiveData =
            new MutableLiveData<>();
    private MutableLiveData<ForgotPasswordCloudResponse> responseLiveData = new MutableLiveData<>();


    public ForgotPasswordViewModel() {
        this.userRepo = UserDataRepository.getInstance();

        formLiveData.setValue(new ForgotPasswordFormState());
    }

    @Override
    protected void onCleared() {
        mDisposables.clear();
    }

    public MutableLiveData<ForgotPasswordFormState> getFormLiveData() {
        return formLiveData;
    }

    public MutableLiveData<ForgotPasswordCloudResponse> getResponseLiveData() {
        return responseLiveData;
    }

    public void forgotPasswordSubmit(String email) {
        mDisposables.add(userRepo.getUserByEmail(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<User>() {
                    @Override
                    public void onStart() {
                        responseLiveData.setValue(ForgotPasswordCloudResponse.LOADING);
                    }

                    @Override
                    public void onSuccess(@NonNull User o) {
                        responseLiveData.setValue(ForgotPasswordCloudResponse.USER_FOUND);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        //check for error, declare NO_INTERNET or NO_USER_FOUND accordingly
                        responseLiveData.setValue(ForgotPasswordCloudResponse.NO_USER_FOUND);
                    }
                })
        );
    }
}
