package com.septech.centauri.ui.user.search;

import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.septech.centauri.data.repository.ItemRepositoryImpl;
import com.septech.centauri.domain.models.Item;
import com.septech.centauri.domain.repository.ItemRepository;
import com.septech.centauri.lib.Zip;

import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class SearchViewModel extends ViewModel {
    private static final String TAG = "SearchViewModel";

    private ItemRepository itemRepo;

    private MutableLiveData<List<Item>> itemsLiveData;
    private MutableLiveData<Map<Integer, Uri>> imagesLiveData;
    private MutableLiveData<Integer> searchAmountLiveData;

    private String query;
    private Integer pageSize;
    private Integer currentPage;

    private final CompositeDisposable mDisposables;

    public SearchViewModel() {
        itemRepo = ItemRepositoryImpl.getInstance();

        mDisposables = new CompositeDisposable();

        currentPage = 0;
        pageSize = 20;
    }

    public void searchItems() {
        mDisposables.add(itemRepo.searchItems(query, currentPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<Item>>() {
                    @Override
                    public void onNext(@NonNull List<Item> items) {
                        Log.i(TAG, "Items received: " + items);
                        itemsLiveData.setValue(items);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        System.out.println("e = " + e);
                    }

                    @Override
                    public void onComplete() {
                    }
                }));
    }

    public void getSearchAmount() {
        mDisposables.add(itemRepo.getAmountInQuery(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Integer>() {
                    @Override
                    public void onSuccess(@NonNull Integer integer) {
                        Log.i(TAG, "Search amount: " + integer);

                        searchAmountLiveData.setValue(integer);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i(TAG, "onError: " + e);
                    }
                }));
    }

    public void lastPage() {
        currentPage -= 1;

        searchItems();
    }

    public void nextPage() {
        currentPage += 1;

        itemsLiveData = new MutableLiveData<>();
        imagesLiveData = new MutableLiveData<>();

        searchItems();
    }

    public void getImages(int[] itemIds) {
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

    public MutableLiveData<Integer> getSearchAmountLiveData() {
        if (searchAmountLiveData == null) {
            searchAmountLiveData = new MutableLiveData<>();
            getSearchAmount();
        }
        return searchAmountLiveData;
    }

    public MutableLiveData<List<Item>> getItemsLiveData() {
        if (itemsLiveData == null) {
            itemsLiveData = new MutableLiveData<>();
            searchItems();
        }
        return itemsLiveData;
    }

    public MutableLiveData<Map<Integer, Uri>> getImagesLiveData() {
        if (imagesLiveData == null) {
            imagesLiveData = new MutableLiveData<>();

            getImages(itemLiveDataToIdList());
        }
        return imagesLiveData;
    }

    private int[] itemLiveDataToIdList() {
        List<Item> items = getItemsLiveData().getValue();

        int[] itemIds = new int[items.size()];

        for (int i = 0; i < items.size(); i++) {
            itemIds[i] = items.get(i).getId();
        }

        return itemIds;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String newQuery) {
        this.query = newQuery;
    }

    public Integer getPageSize() {
        return pageSize;
    }
}
