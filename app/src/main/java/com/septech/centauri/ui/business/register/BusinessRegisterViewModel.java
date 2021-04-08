package com.septech.centauri.ui.business.register;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.septech.centauri.data.model.business.mapper.BusinessDataMapper;
import com.septech.centauri.data.repository.BusinessDataRepository;
import com.septech.centauri.data.repository.UserDataRepository;
import com.septech.centauri.data.utils.PasswordUtils;
import com.septech.centauri.domain.models.Business;
import com.septech.centauri.domain.models.User;
import com.septech.centauri.domain.repository.BusinessRepository;
import com.septech.centauri.domain.repository.UserRepository;
import com.septech.centauri.lib.DateTime;
import com.septech.centauri.ui.user.register.RegisterFormState;
import com.septech.centauri.ui.user.register.RegisterResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class BusinessRegisterViewModel extends ViewModel {
    private static final String TAG = "BusiRegisterViewModel";

    private final MutableLiveData<BusinessRegisterFormState> mRegisterFormState = new MutableLiveData<>();
    private final MutableLiveData<BusinessRegisterResponse> responseLiveData =
            new MutableLiveData<>();

    private final BusinessRepository businessRepo;

    private final CompositeDisposable mDisposables = new CompositeDisposable();

    public BusinessRegisterViewModel() {
        businessRepo = BusinessDataRepository.getInstance();

        mRegisterFormState.setValue(new BusinessRegisterFormState());
    }

    public MutableLiveData<BusinessRegisterResponse> getResponseLiveData() {
        return responseLiveData;
    }

    @Override
    protected void onCleared() {
        mDisposables.clear();
    }

    public void createBusinessAccount(String businessName, String businessOwnerName,
                                      String email,
                                      String password, String phoneNumber) {
        PasswordUtils pwUtils = new PasswordUtils(password);
        String pwHash = pwUtils.hash();

        Business business = new Business();

        business.setBusinessName(businessName);
        business.setOwnerFullName(businessOwnerName);
        business.setEmail(email);
        business.setPassword(pwHash);
        business.setPasswordSalt(pwUtils.getSalt());
        business.setPhoneNumber(phoneNumber);

        mDisposables.add(businessRepo.createBusinessAccount(business)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Business>() {
                    @Override
                    public void onStart() {
                        responseLiveData.setValue(BusinessRegisterResponse.LOADING);
                    }

                    @Override
                    public void onSuccess(@NonNull Business business) {
                        responseLiveData.setValue(BusinessRegisterResponse.SUCCESS);
                        System.out.println("business = " + business);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        responseLiveData.setValue(BusinessRegisterResponse.INFO_INCORRECT);
                        System.out.println("e = " + e);
                    }
                }));
    }
}
