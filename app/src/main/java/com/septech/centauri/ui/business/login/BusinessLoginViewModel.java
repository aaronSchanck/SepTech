package com.septech.centauri.ui.business.login;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.septech.centauri.R;
import com.septech.centauri.data.repository.BusinessDataRepository;
import com.septech.centauri.domain.models.Business;
import com.septech.centauri.domain.repository.BusinessRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class BusinessLoginViewModel extends ViewModel {
    private static final String TAG = "BusiLoginViewModel";

    private final int MAX_LOGIN_TRIES = 5;

    private int loginTries;

    private final BusinessRepository businessRepo;

    private final CompositeDisposable mDisposables = new CompositeDisposable();

    private final MutableLiveData<Business> businessLiveData = new MutableLiveData<>();
    private final MutableLiveData<BusinessLoginResponse> responseLiveData = new MutableLiveData<>();
    private final MutableLiveData<BusinessLoginFormState> loginFormStateLiveData =
            new MutableLiveData<>();

    public BusinessLoginViewModel() {
        this.businessRepo = BusinessDataRepository.getInstance();
        this.loginTries = 0;
    }

    public MutableLiveData<Business> getUserLiveData() {
        return businessLiveData;
    }

    public MutableLiveData<BusinessLoginResponse> getResponseLiveData() {
        return responseLiveData;
    }

    public MutableLiveData<BusinessLoginFormState> getLoginFormStateLiveData() {
        return loginFormStateLiveData;
    }

    @Override
    protected void onCleared() {
        mDisposables.clear();
    }

    public void login(String email, String password) {
        mDisposables.add(businessRepo.getBusinessByEmail(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Business>() {
                    @Override
                    public void onStart() {

                        responseLiveData.setValue(BusinessLoginResponse.SEARCHING);
                    }

                    @Override
                    public void onSuccess(@NonNull Business business) {
                        mDisposables.add(businessRepo.login(email, password, business.getPasswordSalt())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeWith(new DisposableSingleObserver<Business>() {
                                    @Override
                                    public void onSuccess(@NonNull Business business) {
                                        System.out.println("business = " + business);
                                        responseLiveData.setValue(BusinessLoginResponse.BUSINESS_FOUND);
                                        businessLiveData.setValue(business);
                                    }

                                    @Override
                                    public void onError(@NonNull Throwable e) {
                                        System.out.println("e = " + e);
                                        responseLiveData.setValue(BusinessLoginResponse.PASSWORD_INCORRECT);
                                        loginTries += 1;
                                    }
                                })
                        );
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        responseLiveData.setValue(BusinessLoginResponse.NO_BUSINESS_FOUND_FOR_EMAIL);
                    }
                })
        );
    }

    public void onUpdateUsername(String username) {
        //TODO
    }

    public void onUpdatePassword(String password) {
        //TODO
    }

    public void onUpdateLoginInfo(String username, String password) {
        if (!usernameIsValid(username))
            loginFormStateLiveData.setValue(new BusinessLoginFormState(R.string.string_username_incorrect,
                    null));
        else if (!passwordIsValid(password))
            loginFormStateLiveData.setValue(new BusinessLoginFormState(null,
                    R.string.string_password_incorrect));
        else
            loginFormStateLiveData.setValue(new BusinessLoginFormState(true));
    }

    private boolean usernameIsValid(String username) {
        return username.length() > 5;
    }

    private boolean passwordIsValid(String password) {
        return password.length() > 3;
    }
}
