package com.septech.centauri.ui.addlisting;

import androidx.lifecycle.ViewModel;

import com.septech.centauri.data.repository.ItemDataRepository;
import com.septech.centauri.domain.models.Business;
import com.septech.centauri.domain.models.Category;
import com.septech.centauri.domain.models.Item;
import com.septech.centauri.domain.repository.ItemRepository;

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
        itemRepo = ItemDataRepository.getInstance();
    }

    @Override
    protected void onCleared() {
        mDisposables.clear();
    }

    public void createItem(String name, int quantity, boolean bid, boolean buyout,
                           String auctionLength, String startingBid, String minBidIncrement,
                           String buyoutPrice, String mainCategory, String categoryTwo,
                           String categoryThree, String categoryFour, String categoryFive,
                           String itemDescription, String[] imagePaths) {
        Item newItem = new Item();

        newItem.setName(name);
        newItem.setQuantity(quantity);
        newItem.setCanBid(bid);
        newItem.setCanBuy(buyout);
        newItem.setBiddingEnds(auctionLength);
        newItem.setStartingBid(startingBid);
        newItem.setMinBidIncrement(minBidIncrement);
        newItem.setBuyoutPrice(buyoutPrice);
        newItem.setCategory(new Category(mainCategory, categoryTwo, categoryThree, categoryFour,
                categoryFive));
        newItem.setItemDescription(itemDescription);

        mDisposables.add(itemRepo.createItem(newItem, imagePaths)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Item>() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess(@NonNull Item item) {
                        //TODO
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        //TODO
                    }
                })
        );
    }
}
