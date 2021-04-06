package com.septech.centauri.domain.repository;

import com.septech.centauri.domain.models.Item;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * A domain-level interface of a repository for the Items table.
 */
public interface ItemRepository {
    Single<Item> createItem(Item item, List<String> imagePaths);
    Observable<List<Item>> searchItems(String query);
}