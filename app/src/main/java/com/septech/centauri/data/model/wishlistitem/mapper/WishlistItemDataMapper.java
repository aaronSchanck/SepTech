package com.septech.centauri.data.model.wishlistitem.mapper;

import com.septech.centauri.data.model.wishlistitem.WishlistItemEntity;
import com.septech.centauri.domain.models.WishlistItem;

import java.util.ArrayList;
import java.util.List;

public class WishlistItemDataMapper {
    public static WishlistItem transform(WishlistItemEntity wishlistItemEntity) {
        WishlistItem wishlistItem = new WishlistItem();

        return wishlistItem;
    }

    public static WishlistItemEntity transform(WishlistItem wishlistItem) {
        WishlistItemEntity wishlistItemEntity = new WishlistItemEntity();

        return wishlistItemEntity;
    }

    public static List<WishlistItem> transformOrderItemEntityList(List<WishlistItemEntity> wishlistItemEntities) {
        List<WishlistItem> wishlistItems = new ArrayList<>();

        for (WishlistItemEntity wishlistItemEntity :
                wishlistItemEntities) {
            wishlistItems.add(WishlistItemDataMapper.transform(wishlistItemEntity));
        }

        return wishlistItems;
    }

    public static List<WishlistItemEntity> transformOrderItemList(List<WishlistItem> wishlistItems) {
        List<WishlistItemEntity> wishlistItemEntities = new ArrayList<>();

        for (WishlistItem wishlistItem :
                wishlistItems) {
            wishlistItemEntities.add(WishlistItemDataMapper.transform(wishlistItem));
        }

        return wishlistItemEntities;
    }
}

