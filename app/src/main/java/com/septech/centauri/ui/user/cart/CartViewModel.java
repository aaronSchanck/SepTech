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

    private final UserRepository userRepo;
    private final ItemRepository itemRepo;
    private final CompositeDisposable mDisposables;
    private MutableLiveData<Order> orderLiveData;
    private MutableLiveData<Map<Integer, Uri>> imagesLiveData;
    private int userId;

    public CartViewModel() {
        userRepo = UserRepositoryImpl.getInstance();
        itemRepo = ItemRepositoryImpl.getInstance();

        mDisposables = new CompositeDisposable();
    }

    public BigDecimal getItemBasePrice() {
        List<OrderItem> orderItemList = orderLiveData.getValue().getOrderItems();

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
        return Rates.SHIPPING_RATE;
    }

    public BigDecimal getTotalPrice() {
        return getItemBasePrice().add(getTax()).add(getShippingPrice()).setScale(2, RoundingMode.HALF_EVEN);
    }

    public void getOrder() {
        mDisposables.add(userRepo.getUserCart(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Order>() {
                    @Override
                    public void onSuccess(@NonNull Order order) {
                        orderLiveData.setValue(order);
                        System.out.println("order = " + order);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        System.out.println("e = " + e);
                    }
                }));
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

    private int[] orderLiveDataToItemIdArray() {
        List<OrderItem> orderItems = getOrderLiveData().getValue().getOrderItems();

        int[] itemIds = new int[orderItems.size()];

        for (int i = 0; i < orderItems.size(); i++) {
            itemIds[i] = orderItems.get(i).getItemid();
        }

        return itemIds;
    }

    public MutableLiveData<Order> getOrderLiveData() {
        if (orderLiveData == null) {
            orderLiveData = new MutableLiveData<>();
            getOrder();
        }
        return orderLiveData;
    }

    public MutableLiveData<Map<Integer, Uri>> getImagesLiveData() {
        if (imagesLiveData == null) {
            imagesLiveData = new MutableLiveData<>();
            getImages(orderLiveDataToItemIdArray());
        }
        return imagesLiveData;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setOrderLiveData(Order order) {
        if (orderLiveData == null) {
            orderLiveData = new MutableLiveData<>();
        }
        orderLiveData.setValue(order);
    }
}