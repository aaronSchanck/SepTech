package com.septech.centauri.ui.business.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.septech.centauri.data.repository.BusinessDataRepository;
import com.septech.centauri.domain.models.Business;
import com.septech.centauri.domain.repository.BusinessRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class BusinessHomeViewModel extends ViewModel {

    private MutableLiveData<Business> mBusinessLiveData = new MutableLiveData<>();

    private final BusinessRepository businessRepo;

    private final CompositeDisposable mDisposables = new CompositeDisposable();

    public BusinessHomeViewModel() {
        businessRepo = BusinessDataRepository.getInstance();
    }

    public void setBusiness(int id) {
        mDisposables.add(businessRepo.getBusinessById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Business>() {
                    @Override
                    public void onSuccess(@NonNull Business business) {
                        mBusinessLiveData.setValue(business);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        System.out.println("e = " + e);
                    }
                }));
    }

    public MutableLiveData<Business> getBusinessLiveData() {
        return mBusinessLiveData;
    }
}
