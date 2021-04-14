package com.septech.centauri.ui.user.listing;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.septech.centauri.data.repository.ItemDataRepository;
import com.septech.centauri.domain.models.Item;
import com.septech.centauri.domain.models.ItemReview;
import com.septech.centauri.domain.repository.ItemRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ListingViewModel extends ViewModel {

    private ItemRepository itemRepo;

    private Integer itemId;

    private MutableLiveData<Item> item = new MutableLiveData<>();
    private MutableLiveData<List<ItemReview>> reviews = new MutableLiveData<>();

    private CompositeDisposable mDisposables = new CompositeDisposable();

    public ListingViewModel() {
        itemRepo = ItemDataRepository.getInstance();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mDisposables.clear();
    }

    public void getItem(int id) {
        this.itemId = id;

        mDisposables.add(itemRepo.getItemById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Item>() {
                    @Override
                    public void onSuccess(@NonNull Item item) {
                        System.out.println("item = " + item);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        System.out.println("e = " + e);
                    }
                }));
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public MutableLiveData<Item> getItem() {
        return item;
    }

    public MutableLiveData<List<ItemReview>> getReviews() {
        return reviews;
    }
}