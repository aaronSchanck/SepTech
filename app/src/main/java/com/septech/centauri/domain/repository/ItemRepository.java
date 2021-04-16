package com.septech.centauri.domain.repository;

import com.septech.centauri.domain.models.Item;

import java.io.File;
import java.util.List;
import java.util.Optional;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * A domain-level interface of a repository for the Items table.
 */
public interface ItemRepository {
    Single<Item> getItemById(int id);

    Single<Item> createItem(Item item, List<String> imagePaths);

    Observable<List<Item>> searchItems(String query, int page);

    Observable<Response<ResponseBody>> getItemThumbnails(int[] itemIds);

    Single<Integer> getAmountInQuery(String query);

    Observable<Response<ResponseBody>> getImages(int itemId);
}
