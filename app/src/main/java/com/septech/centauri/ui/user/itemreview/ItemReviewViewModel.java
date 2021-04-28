package com.septech.centauri.ui.user.itemreview;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.septech.centauri.data.repository.ItemRepositoryImpl;
import com.septech.centauri.domain.models.ItemReview;
import com.septech.centauri.domain.repository.ItemRepository;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ItemReviewViewModel extends ViewModel {
    private final ItemRepository itemRepo;

    private MutableLiveData<ItemReview> itemReviewLiveData;

    private final CompositeDisposable mDisposables;

    private int itemid;

    public ItemReviewViewModel() {
        itemRepo = ItemRepositoryImpl.getInstance();

        mDisposables = new CompositeDisposable();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mDisposables.clear();
    }

    public void addItemReview(int userid, int itemid, float rating, String content) {
        ItemReview itemReview = new ItemReview(userid, itemid, rating, content);

        mDisposables.add(itemRepo.addItemReview(itemReview)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ItemReview>() {
                    @Override
                    public void onSuccess(@NonNull ItemReview itemReview) {
                        itemReviewLiveData.setValue(itemReview);
                        System.out.println("itemReview = " + itemReview);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        System.out.println("e = " + e);
                    }
                }));
    }

    public int getItemid() {
        return itemid;
    }

    public void setItemid(int itemid) {
        this.itemid = itemid;
    }

    public MutableLiveData<ItemReview> getItemReviewLiveData() {
        if(itemReviewLiveData == null) {
            itemReviewLiveData = new MutableLiveData<>();
        }
        return itemReviewLiveData;
    }
}