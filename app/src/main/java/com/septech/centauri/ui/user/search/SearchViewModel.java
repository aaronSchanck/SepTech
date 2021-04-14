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

    private MutableLiveData<List<Item>> itemsLiveData = new MutableLiveData<>();
    private MutableLiveData<Map<Integer, Uri>> imagesLiveData = new MutableLiveData<>();
    private MutableLiveData<Integer> searchAmount = new MutableLiveData<>();

    private String currentQuery;
    private Integer pageSize;
    private Integer currentPage;

    private final CompositeDisposable mDisposables = new CompositeDisposable();

    public SearchViewModel() {
        itemRepo = ItemRepositoryImpl.getInstance();

        currentPage = 0;
        pageSize = 20;
    }

    public void search() {
        mDisposables.add(itemRepo.searchItems(currentQuery, currentPage)
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

    public void newSearch(String query) {
        currentPage = 0;
        currentQuery = query;

        mDisposables.add(itemRepo.getAmountInQuery(currentQuery)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Integer>() {
                    @Override
                    public void onSuccess(@NonNull Integer integer) {
                        Log.i(TAG, "Search amount: " + integer);

                        searchAmount.setValue(integer);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i(TAG, "onError: " + e);
                    }
                }));

        search();
    }

    public void lastPage() {
        currentPage -= 1;

        search();
    }

    public void nextPage() {
        currentPage += 1;

        search();
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

    public MutableLiveData<Integer> getSearchAmount() {
        return searchAmount;
    }

    public MutableLiveData<List<Item>> getItemsLiveData() {
        return itemsLiveData;
    }

    public MutableLiveData<Map<Integer, Uri>> getImagesLiveData() {
        return imagesLiveData;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public String getCurrentQuery() {
        return currentQuery;
    }

    public Integer getPageSize() {
        return pageSize;
    }
}
