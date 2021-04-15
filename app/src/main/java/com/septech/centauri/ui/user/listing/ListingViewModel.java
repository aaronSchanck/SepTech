package com.septech.centauri.ui.user.listing;

import android.net.Uri;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.septech.centauri.data.repository.BusinessRepositoryImpl;
import com.septech.centauri.data.repository.ItemRepositoryImpl;
import com.septech.centauri.domain.models.Business;
import com.septech.centauri.domain.models.Item;
import com.septech.centauri.domain.models.ItemReview;
import com.septech.centauri.domain.repository.BusinessRepository;
import com.septech.centauri.domain.repository.ItemRepository;
import com.septech.centauri.lib.Zip;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ListingViewModel extends ViewModel {

    private ItemRepository itemRepo;
    private BusinessRepository businessRepo;

    private Integer itemId;

    private int currentImage;
    private int currentQuantity;

    private MutableLiveData<Item> itemLiveData = new MutableLiveData<>();
    private MutableLiveData<Business> businessLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Uri>> imageLiveData = new MutableLiveData<>();
    private MutableLiveData<List<ItemReview>> reviews = new MutableLiveData<>();

    private CompositeDisposable mDisposables = new CompositeDisposable();

    public ListingViewModel() {
        itemRepo = ItemRepositoryImpl.getInstance();
        businessRepo = BusinessRepositoryImpl.getInstance();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mDisposables.clear();
    }

    public void addToCart(Item item, int currentQuantity) {

    }

    public void getImages(int id) {
        mDisposables.add(itemRepo.getImages(id)
                .flatMap(Zip.processResponse())
                .retry(25)
                .flatMap(Zip.unpackZipImages())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<Uri>>() {
                    @Override
                    public void onNext(@NonNull List<Uri> uris) {
                        System.out.println("uris = " + uris);
                        imageLiveData.setValue(uris);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        System.out.println("e = " + e);
                    }

                    @Override
                    public void onComplete() {
                        System.out.println();
                    }
                }));
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
                        itemLiveData.setValue(item);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        System.out.println("e = " + e);
                    }
                }));
    }

    public void getBusiness(int id) {
        mDisposables.add(businessRepo.getBusinessById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Business>() {
                    @Override
                    public void onSuccess(@NonNull Business business) {
                        System.out.println("business = " + business);
                        businessLiveData.setValue(business);
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

    public MutableLiveData<Item> getItemLiveData() {
        return itemLiveData;
    }

    public MutableLiveData<List<ItemReview>> getReviews() {
        return reviews;
    }

    public MutableLiveData<List<Uri>> getImageLiveData() {
        return imageLiveData;
    }

    public int getCurrentImage() {
        return currentImage;
    }

    public void setCurrentImage(int currentImage) {
        this.currentImage = currentImage;
    }

    public int getCurrentQuantity() {
        return currentQuantity;
    }

    public void setCurrentQuantity(int currentQuantity) {
        this.currentQuantity = currentQuantity;
    }

    public MutableLiveData<Business> getBusinessLiveData() {
        return businessLiveData;
    }
}