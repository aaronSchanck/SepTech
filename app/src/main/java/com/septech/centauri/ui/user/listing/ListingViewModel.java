package com.septech.centauri.ui.user.listing;

import android.net.Uri;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.septech.centauri.data.repository.BusinessRepositoryImpl;
import com.septech.centauri.data.repository.ItemRepositoryImpl;
import com.septech.centauri.data.repository.UserRepositoryImpl;
import com.septech.centauri.domain.models.Business;
import com.septech.centauri.domain.models.Item;
import com.septech.centauri.domain.models.ItemReview;
import com.septech.centauri.domain.models.Order;
import com.septech.centauri.domain.models.User;
import com.septech.centauri.domain.models.Wishlist;
import com.septech.centauri.domain.models.WishlistItem;
import com.septech.centauri.domain.repository.BusinessRepository;
import com.septech.centauri.domain.repository.ItemRepository;
import com.septech.centauri.domain.repository.UserRepository;
import com.septech.centauri.lib.Zip;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ListingViewModel extends ViewModel {

    private final UserRepository userRepo;
    private final ItemRepository itemRepo;
    private final BusinessRepository businessRepo;

    private Integer userId;
    private Integer itemId;

    private int currentImage;
    private int currentQuantity;

    private MutableLiveData<Item> itemLiveData;
    private MutableLiveData<Business> businessLiveData;
    private MutableLiveData<List<Uri>> imageLiveData;
    private MutableLiveData<List<ItemReview>> reviews;
    private MutableLiveData<Order> orderLiveData;
    private MutableLiveData<Wishlist> wishlistLiveData;

    private final CompositeDisposable mDisposables;

    public ListingViewModel() {
        userRepo = UserRepositoryImpl.getInstance();
        itemRepo = ItemRepositoryImpl.getInstance();
        businessRepo = BusinessRepositoryImpl.getInstance();
        mDisposables = new CompositeDisposable();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mDisposables.clear();
    }

    public void addToWishlist(User user, Item item) {
        mDisposables.add(userRepo.addToWishlist(user, item)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Wishlist>() {
                    @Override
                    public void onSuccess(@NonNull Wishlist wishlist) {
                        System.out.println("wishlist = " + wishlist);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        System.out.println("e = " + e);
                    }
                }));
    }

    public void addToCart(User user, Item item, int quantity) {
        System.out.println("user = " + user + ", item = " + item + ", quantity = " + quantity);
        mDisposables.add(userRepo.addToCart(user, item, quantity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Order>() {
                    @Override
                    public void onSuccess(@NonNull Order order) {
                        System.out.println("order = " + order);
                        orderLiveData.setValue(order);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        System.out.println("e = " + e);
                    }
                }));
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

    public void getItemDetails(int id) {
        mDisposables.add(itemRepo.getItemDetails(id)
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
        mDisposables.add(itemRepo.getItemById(id)
                .flatMap(item -> businessRepo.getBusinessById(item.getSellerId()))
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

    private void getUserWishlist() {
        mDisposables.add(userRepo.getUserWishlist(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Wishlist>() {
                    @Override
                    public void onSuccess(@NonNull Wishlist wishlist) {
                        wishlistLiveData.setValue(wishlist);
//                        System.out.println("wishlist = " + wishlist);
//                        System.out.println("wishlist = " + wishlist.getWishlistItems());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        System.out.println("e = " + e);
                    }
                }));
    }

    private void checkWishlistItem() {
        mDisposables.add(userRepo.getUserWishlistItem(userId, itemId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<WishlistItem>() {
                    @Override
                    public void onSuccess(@NonNull WishlistItem wishlistItem) {
                        System.out.println("wishlistItem = " + wishlistItem);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        System.out.println("e = " + e);
                    }
                }));
    }

    public MutableLiveData<Business> getBusinessLiveData() {
        if (businessLiveData == null) {
            businessLiveData = new MutableLiveData<>();
            getBusiness(itemId);
        }
        return businessLiveData;
    }

    public MutableLiveData<Item> getItemLiveData() {
        if (itemLiveData == null) {
            itemLiveData = new MutableLiveData<>();
            getItemDetails(itemId);
        }
        return itemLiveData;
    }

    public MutableLiveData<List<ItemReview>> getReviews() {
        if (reviews == null) {
            reviews = new MutableLiveData<>();
            getReviews();
        }
        return reviews;
    }

    public MutableLiveData<List<Uri>> getImageLiveData() {
        if (imageLiveData == null) {
            imageLiveData = new MutableLiveData<>();
            getImages(itemId);
        }
        return imageLiveData;
    }

    public MutableLiveData<Order> getOrderLiveData() {
        if (orderLiveData == null) {
            orderLiveData = new MutableLiveData<>();
        }
        return orderLiveData;
    }

    public MutableLiveData<Wishlist> getWishlistLiveData() {
        if (wishlistLiveData == null) {
            wishlistLiveData = new MutableLiveData<>();
            getUserWishlist();
        }
        return wishlistLiveData;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}