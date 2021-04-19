package com.septech.centauri.ui.business.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.septech.centauri.data.repository.BusinessRepositoryImpl;
import com.septech.centauri.domain.models.Business;
import com.septech.centauri.domain.repository.BusinessRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class BusinessViewModel extends ViewModel {

    private final BusinessRepository businessRepo;

    private MutableLiveData<Business> businessLiveData;

    private final CompositeDisposable mDisposables = new CompositeDisposable();

    private int businessid;

    public BusinessViewModel() {
        businessRepo = BusinessRepositoryImpl.getInstance();
    }

    public void getBusiness() {
        mDisposables.add(businessRepo.getBusinessById(businessid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Business>() {
                    @Override
                    public void onSuccess(@NonNull Business business) {
                        businessLiveData.setValue(business);
                        System.out.println("business = " + business);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        System.out.println("e = " + e);
                    }
                }));
    }

    public MutableLiveData<Business> getBusinessLiveData() {
        if(businessLiveData == null) {
            businessLiveData = new MutableLiveData<>();
            getBusiness();
        }
        return businessLiveData;
    }

    public int getBusinessId() {
        return businessid;
    }

    public void setBusinessId(int businessid) {
        this.businessid = businessid;
    }
}
