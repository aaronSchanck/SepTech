package com.septech.centauri.ui.user.search;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.septech.centauri.data.repository.ItemDataRepository;
import com.septech.centauri.domain.models.Item;
import com.septech.centauri.domain.repository.ItemRepository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchViewModel extends ViewModel {
    private ItemRepository itemRepo;

    private MutableLiveData<List<Item>> itemsLiveData = new MutableLiveData<>();

    private final CompositeDisposable mDisposables = new CompositeDisposable();

    public SearchViewModel() {
        itemRepo = ItemDataRepository.getInstance();
    }

    public void getItems(String query) {
        mDisposables.add(itemRepo.searchItems(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<Item>>() {
                    @Override
                    public void onNext(@NonNull List<Item> items) {
                        System.out.println("items = " + items);
                        itemsLiveData.setValue(items);

                        for (Item i : items) {
                            mDisposables.add(itemRepo.getImagesZip(i.getId())
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribeWith(new DisposableSingleObserver<ResponseBody>() {

                                        @Override
                                        public void onSuccess(@NonNull ResponseBody responseBody) {

                                        }

                                        @Override
                                        public void onError(@NonNull Throwable e) {

                                        }
                                    }));
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        System.out.println("e = " + e);
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("words");
                    }
                }));
    }

    public MutableLiveData<List<Item>> getItemsLiveData() {
        return itemsLiveData;
    }
}
