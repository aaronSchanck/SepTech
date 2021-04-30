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
    private final UserRepository userRepo;

    private final CompositeDisposable mDisposables;

    private MutableLiveData<User> userLiveData;
    private MutableLiveData<Order> cartLiveData;
    private MutableLiveData<Wishlist> wishlistLiveData;

    private int userId;

    public UserViewModel() {
        userRepo = UserRepositoryImpl.getInstance();

        mDisposables = new CompositeDisposable();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mDisposables.clear();
    }

    private void getUser() {
        mDisposables.add(userRepo.getUserById(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<User>() {
                    @Override
                    public void onSuccess(@NonNull User user) {
                        System.out.println("user = " + user);
                        userLiveData.setValue(user);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        //failed

                        System.out.println("e = " + e);
                    }
                }));
    }

    private void getUserCart() {
        mDisposables.add(userRepo.getUserCart(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Order>() {
                    @Override
                    public void onSuccess(@NonNull Order order) {
                        System.out.println("cart = " + order);
                        cartLiveData.setValue(order);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        //failed
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
                        System.out.println("wishlist = " + wishlist);
                        wishlistLiveData.setValue(wishlist);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        //failed
                        System.out.println("e = " + e);
                    }
                }));
    }

    public void updateOrderData(Order order) {
        if (cartLiveData == null) {
            cartLiveData = new MutableLiveData<>();
        }
        cartLiveData.setValue(order);
    }

//    public String getName() {
//        if (userLiveData == null) {
//            return "Guest";
//        }
//        return userLiveData.getValue().getUsername();
//    }

    public MutableLiveData<Wishlist> getWishlistLiveData() {
        if (wishlistLiveData == null) {
            wishlistLiveData = new MutableLiveData<>();
            getUserWishlist();
        }
        return wishlistLiveData;
    }

    public MutableLiveData<User> getUserLiveData() {
        if (userLiveData == null) {
            userLiveData = new MutableLiveData<>();
            getUser();
        }
        return userLiveData;
    }

    public MutableLiveData<Order> getOrderLiveData() {
        if (cartLiveData == null) {
            cartLiveData = new MutableLiveData<>();
            getUserCart();
        }
        return cartLiveData;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
