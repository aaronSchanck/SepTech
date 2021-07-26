package com.septech.centauri.ui.user.search;

import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.septech.centauri.data.repository.ItemRepositoryImpl;
import com.septech.centauri.domain.models.Item;
import com.septech.centauri.domain.models.SearchFilters;
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

    private ItemRepository mItemRepo;

    private MutableLiveData<List<Item>> mItemsLiveData;
    private MutableLiveData<Map<Integer, Uri>> mImagesLiveData;
    private MutableLiveData<Integer> mSearchAmountLiveData;

    private SearchFilters mSearchFilters;
    private String mQuery;
    private Integer mPageSize;
    private Integer mCurrentPage;

    private long mSearchStartTime;
    private long mSearchEndtime;

    private final CompositeDisposable mDisposables;

    public SearchViewModel() {
        mItemRepo = ItemRepositoryImpl.getInstance();

        mDisposables = new CompositeDisposable();

        mCurrentPage = 0;
        mPageSize = 5;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mDisposables.clear();
    }

    public void searchItems() {
        mDisposables.add(mItemRepo.searchItems(mQuery, mCurrentPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<Item>>() {
                    @Override
                    public void onNext(@NonNull List<Item> items) {
                        Log.i(TAG, "Items received: " + items);
                        mItemsLiveData.setValue(items);
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
        mDisposables.add(mItemRepo.getAmountInQuery(mQuery)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Integer>() {
                    @Override
                    protected void onStart() {
                        super.onStart();
                        mSearchStartTime = System.currentTimeMillis();
                    }

                    @Override
                    public void onSuccess(@NonNull Integer integer) {
                        Log.i(TAG, "Search amount: " + integer);

                        mSearchEndtime = System.currentTimeMillis();

                        mSearchAmountLiveData.setValue(integer);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i(TAG, "onError: " + e);
                    }
                }));
    }

    public void lastPage() {
        mCurrentPage -= 1;

        mImagesLiveData = null;

        searchItems();
    }

    public void nextPage() {
        mCurrentPage += 1;

        mImagesLiveData = null;

        searchItems();
    }

    public void getImages(int[] itemIds) {
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

    public MutableLiveData<Integer> getSearchAmountLiveData() {
        if (mSearchAmountLiveData == null) {
            mSearchAmountLiveData = new MutableLiveData<>();
            getSearchAmount();
        }
        return mSearchAmountLiveData;
    }

    public MutableLiveData<List<Item>> getItemsLiveData() {
        if (mItemsLiveData == null) {
            mItemsLiveData = new MutableLiveData<>();
            searchItems();
        }
        return mItemsLiveData;
    }

    public MutableLiveData<Map<Integer, Uri>> getImagesLiveData() {
        if (mImagesLiveData == null) {
            mImagesLiveData = new MutableLiveData<>();
            getImages(itemLiveDataToIdList());
        }
        return mImagesLiveData;
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
        return mCurrentPage;
    }

    public String getQuery() {
        return mQuery;
    }

    public void setQuery(String newQuery) {
        this.mQuery = newQuery;
    }

    public Integer getPageSize() {
        return mPageSize;
    }

    public double getSearchTime() {
        Log.w("sd", String.valueOf(mSearchEndtime - mSearchStartTime));
        return (mSearchEndtime - mSearchStartTime) / 1000.0;
    }

    public void setSearchFilters(SearchFilters searchFilters) {
        mSearchFilters = searchFilters;
    }
}
