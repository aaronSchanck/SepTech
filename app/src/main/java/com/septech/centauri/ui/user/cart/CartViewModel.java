package com.septech.centauri.ui.user.cart;

import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.septech.centauri.data.repository.ItemRepositoryImpl;
import com.septech.centauri.data.repository.UserRepositoryImpl;
import com.septech.centauri.domain.models.Order;
import com.septech.centauri.domain.models.OrderItem;
import com.septech.centauri.domain.repository.ItemRepository;
import com.septech.centauri.domain.repository.UserRepository;
import com.septech.centauri.lib.Rates;
import com.septech.centauri.lib.Zip;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class CartViewModel extends ViewModel {
    private static final String TAG = "CartViewModel";

    private final UserRepository mUserRepo;
    private final ItemRepository mItemRepo;
    private final CompositeDisposable mDisposables;
    private MutableLiveData<Order> mOrderLiveData;
    private MutableLiveData<Map<Integer, Uri>> mImagesLiveData;
    private int userId;

    public CartViewModel() {
        mUserRepo = UserRepositoryImpl.getInstance();
        mItemRepo = ItemRepositoryImpl.getInstance();

        mDisposables = new CompositeDisposable();
    }

    public BigDecimal getItemBasePrice() {
        if(mOrderLiveData == null) {
            return new BigDecimal("0");
        }

        if(mOrderLiveData.getValue() == null) {
            return new BigDecimal("0");
        }

        List<OrderItem> orderItemList = mOrderLiveData.getValue().getOrderItems();

        BigDecimal totalPrice = new BigDecimal("0");

        for (OrderItem orderItem : orderItemList) {
            BigDecimal quantity = new BigDecimal(String.valueOf(orderItem.getQuantity()));

            totalPrice = totalPrice.add(orderItem.getItem().getBigDecDollarPrice().multiply(quantity));
        }

        return totalPrice;
    }

    public BigDecimal getTax() {
        return Rates.TAX_RATE.multiply(getItemBasePrice()).setScale(2, RoundingMode.HALF_EVEN);
    }

    public BigDecimal getShippingPrice() {
        if(mOrderLiveData == null) {
            return new BigDecimal("0");
        }

        if(mOrderLiveData.getValue() == null) {
            return new BigDecimal("0");
        }

        return Rates.SHIPPING_RATE;
    }

    public BigDecimal getTotalPrice() {
        return getItemBasePrice().add(getTax()).add(getShippingPrice()).setScale(2, RoundingMode.HALF_EVEN);
    }

    public void getOrder() {
        mDisposables.add(mUserRepo.getUserCart(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Order>() {
                    @Override
                    public void onSuccess(@NonNull Order order) {
                        mOrderLiveData.setValue(order);
                        System.out.println("order = " + order);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        System.out.println("e = " + e);
                    }
                }));
    }

    private void getImages(int[] itemIds) {
        mDisposables.add(mItemRepo.getItemThumbnails(itemIds)
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
                        mImagesLiveData.setValue(map);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "Error in conversion: " + e);
                    }
                }));
    }

    private int[] orderLiveDataToItemIdArray() {
        if(mOrderLiveData == null) {
            return new int[0];
        }

        if(mOrderLiveData.getValue() == null) {
            return new int[0];
        }

        List<OrderItem> orderItems = mOrderLiveData.getValue().getOrderItems();

        int[] itemIds = new int[orderItems.size()];

        for (int i = 0; i < orderItems.size(); i++) {
            itemIds[i] = orderItems.get(i).getItemid();
        }

        return itemIds;
    }

    public MutableLiveData<Map<Integer, Uri>> getImagesLiveData() {
        if (mImagesLiveData == null) {
            mImagesLiveData = new MutableLiveData<>();
            getImages(orderLiveDataToItemIdArray());
        }
        return mImagesLiveData;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setOrderLiveData(Order order) {
        if (mOrderLiveData == null) {
            mOrderLiveData = new MutableLiveData<>();
        }
        mOrderLiveData.setValue(order);
    }
}