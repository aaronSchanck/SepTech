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

public class BusinessHomeViewModel extends ViewModel {

    private final BusinessRepository businessRepo;

    private final CompositeDisposable mDisposables = new CompositeDisposable();

    public BusinessHomeViewModel() {
        businessRepo = BusinessRepositoryImpl.getInstance();
    }
}
