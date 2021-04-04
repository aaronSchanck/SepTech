package com.septech.centauri.domain.repository;

import com.septech.centauri.domain.models.Item;

import java.util.List;

import io.reactivex.Single;

public interface ItemRepository {

    Single<Item> createItem(Item item, List<String> imagePaths);
}
