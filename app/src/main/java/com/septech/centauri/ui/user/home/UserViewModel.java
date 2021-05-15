package com.septech.centauri.ui.user.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.septech.centauri.data.repository.UserRepositoryImpl;
import com.septech.centauri.domain.models.Order;
import com.septech.centauri.domain.models.User;
import com.septech.centauri.domain.models.Wishlist;
import com.septech.centauri.domain.repository.UserRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class UserViewModel extends ViewModel {
    private final UserRepository mUserRepo;

    private final CompositeDisposable mDisposables;

    private MutableLiveData<User> mUserLiveData;

    private MutableLiveData<Order> mCartLiveData;
    private MutableLiveData<Wishlist> mWishlistLiveData;

    private boolean mIsProcessingCart;
    private boolean mIsProcessingWishlist;

    private int mUserId;

    public UserViewModel() {
        mUserRepo = UserRepositoryImpl.getInstance();

        mDisposables = new CompositeDisposable();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mDisposables.clear();
    }

    private void getUser() {
        mDisposables.add(mUserRepo.getUserById(mUserId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<User>() {
                    @Override
                    public void onSuccess(@NonNull User user) {
                        System.out.println("user = " + user);
                        mUserLiveData.setValue(user);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        //failed

                        System.out.println("e = " + e);
                    }
                }));
    }

    private void getUserCart() {
        mIsProcessingCart = true;

        mDisposables.add(mUserRepo.getUserCart(mUserId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Order>() {
                    @Override
                    public void onSuccess(@NonNull Order order) {
                        System.out.println("cart = " + order);
                        mCartLiveData.setValue(order);

                        mIsProcessingCart = false;
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        //failed
                        System.out.println("e = " + e);

                        mIsProcessingCart = false;
                    }
                }));
    }

    private void getUserWishlist() {
        mIsProcessingWishlist = true;

        mDisposables.add(mUserRepo.getUserWishlist(mUserId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Wishlist>() {
                    @Override
                    public void onSuccess(@NonNull Wishlist wishlist) {
                        System.out.println("wishlist = " + wishlist);
                        mWishlistLiveData.setValue(wishlist);

                        mIsProcessingWishlist = false;
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        //failed
                        System.out.println("e = " + e);

                        mIsProcessingWishlist = false;
                    }
                }));
    }

    public void updateOrderData(Order order) {
        if (mCartLiveData == null) {
            mCartLiveData = new MutableLiveData<>();
        }
        mCartLiveData.setValue(order);
    }

//    public String getName() {
//        if (userLiveData == null) {
//            return "Guest";
//        }
//        return userLiveData.getValue().getUsername();
//    }

    public MutableLiveData<Wishlist> getWishlistLiveData() {
        if (mWishlistLiveData == null) {
            mWishlistLiveData = new MutableLiveData<>();
            getUserWishlist();
        }
        return mWishlistLiveData;
    }

    public MutableLiveData<User> getUserLiveData() {
        if (mUserLiveData == null) {
            mUserLiveData = new MutableLiveData<>();
            getUser();
        }
        return mUserLiveData;
    }

    public MutableLiveData<Order> getOrderLiveData() {
        if (mCartLiveData == null) {
            mCartLiveData = new MutableLiveData<>();
            getUserCart();
        }
        return mCartLiveData;
    }

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int userId) {
        this.mUserId = userId;
    }

    public void refreshUserCart() {
        if(!mIsProcessingCart) getUserCart();
    }

    public void refreshUserWishlist() {
        if(!mIsProcessingWishlist) getUserWishlist();
    }
}
