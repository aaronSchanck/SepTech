package com.septech.centauri.ui.user.wishlist;

import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.septech.centauri.data.repository.ItemRepositoryImpl;
import com.septech.centauri.data.repository.UserRepositoryImpl;
import com.septech.centauri.domain.models.Order;
import com.septech.centauri.domain.models.OrderItem;
import com.septech.centauri.domain.models.Wishlist;
import com.septech.centauri.domain.models.WishlistItem;
import com.septech.centauri.domain.repository.ItemRepository;
import com.septech.centauri.domain.repository.UserRepository;
import com.septech.centauri.lib.Zip;

import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class WishlistViewModel extends ViewModel {
    private static final String TAG = "WishlistViewModel";

    private final UserRepository userRepo;
    private final ItemRepository itemRepo;

    private final CompositeDisposable mDisposables;

    private MutableLiveData<Wishlist> wishlistLiveData;
    private MutableLiveData<Map<Integer, Uri>> imagesLiveData;

    public WishlistViewModel() {
        this.userRepo = UserRepositoryImpl.getInstance();
        this.itemRepo = ItemRepositoryImpl.getInstance();

        this.mDisposables = new CompositeDisposable();
    }

    private void getImages(int[] itemIds) {
        mDisposables.add(itemRepo.getItemThumbnails(itemIds)
                .flatMap(Zip.processResponse())
                .retry(25) //must be used when running the emulator, don't ask. I don't know why
                .flatMap(Zip.unpackZipThumbnails())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Map<Integer, Uri>>() {
                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onCompleted");
                    }

                    @Override
                    public void onNext(@NonNull Map<Integer, Uri> map) {
                        imagesLiveData.setValue(map);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "Error in conversion: " + e);
                    }
                }));
    }

    private int[] wishlistLiveDataToItemIdArray() {
        List<WishlistItem> wishlistItems = wishlistLiveData.getValue().getWishlistItems();

        int[] itemIds = new int[wishlistItems.size()];

        for (int i = 0; i < wishlistItems.size(); i++) {
            itemIds[i] = wishlistItems.get(i).getItemid();
        }

        return itemIds;
    }

    public MutableLiveData<Wishlist> getWishlistLiveData() {
        if (wishlistLiveData == null) {
            wishlistLiveData = new MutableLiveData<>();
        }
        return wishlistLiveData;
    }

    public MutableLiveData<Map<Integer, Uri>> getImagesLiveData() {
        if (imagesLiveData == null) {
            imagesLiveData = new MutableLiveData<>();
            getImages(wishlistLiveDataToItemIdArray());
        }
        return imagesLiveData;
    }

    public void setWishlistLiveData(Wishlist wishlist) {
        if(wishlistLiveData == null) {
            wishlistLiveData = new MutableLiveData<>();
        }

        wishlistLiveData.setValue(wishlist);
    }
}