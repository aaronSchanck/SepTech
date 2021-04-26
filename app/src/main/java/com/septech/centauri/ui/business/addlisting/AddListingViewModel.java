package com.septech.centauri.ui.business.addlisting;

import androidx.lifecycle.ViewModel;

import com.septech.centauri.data.repository.ItemRepositoryImpl;
import com.septech.centauri.domain.models.Business;
import com.septech.centauri.domain.models.Category;
import com.septech.centauri.domain.models.Item;
import com.septech.centauri.domain.repository.ItemRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class AddListingViewModel extends ViewModel {
    private final ItemRepository itemRepo;

    private Business business;

    private final CompositeDisposable mDisposables = new CompositeDisposable();

    public AddListingViewModel() {
        itemRepo = ItemRepositoryImpl.getInstance();
    }

    @Override
    protected void onCleared() {
        mDisposables.clear();
    }

    public void createItem(int sellerId, String name, String quality, int quantity, boolean bid,
                           boolean buyout, String auctionLength, String startingBid,
                           String minBidIncrement, String buyoutPrice, String mainCategory,
                           String categoryTwo, String categoryThree, String categoryFour,
                           String categoryFive, String itemDescription, List<String> imagePaths) {
        Item newItem = new Item();

        newItem.setSellerId(sellerId);
        newItem.setName(name);
        newItem.setQuality(quality);
        newItem.setQuantity(quantity);

        newItem.setCanBid(bid);
        newItem.setCanBuy(buyout);

        newItem.setBiddingEnds(auctionLength);

        newItem.setStartingBid(getMoneyValue(startingBid));
        newItem.setMinBidIncrement(getMoneyValue(minBidIncrement));
        newItem.setBuyoutPrice(getMoneyValue(buyoutPrice));

        newItem.setCategory(new Category(mainCategory, categoryTwo, categoryThree, categoryFour,
                categoryFive));
        newItem.setDescription(itemDescription);


        mDisposables.add(itemRepo.createItem(newItem, imagePaths)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Item>() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess(@NonNull Item item) {
                        System.out.println("item = " + item);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        System.out.println("e = " + e);
                    }
                })
        );
    }

    public Long getMoneyValue(String stringValue) {
        if(stringValue == null || stringValue.equals("")) {
            return null;
        }

        String newString = stringValue.replace(".", "");

        return Long.parseLong(newString);
    }
}
