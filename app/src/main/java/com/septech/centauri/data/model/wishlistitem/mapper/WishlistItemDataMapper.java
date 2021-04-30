package com.septech.centauri.data.model.wishlistitem.mapper;

import com.septech.centauri.data.model.item.mapper.ItemDataMapper;
import com.septech.centauri.data.model.wishlistitem.WishlistItemEntity;
import com.septech.centauri.domain.models.WishlistItem;

import java.util.ArrayList;
import java.util.List;

public class WishlistItemDataMapper {
    public static WishlistItem transform(WishlistItemEntity wishlistItemEntity) {
        WishlistItem wishlistItem = new WishlistItem();

        wishlistItem.setId(wishlistItemEntity.getId());
        wishlistItem.setItemid(wishlistItemEntity.getItemid());
        wishlistItem.setWishlistid(wishlistItemEntity.getWishlistid());
        wishlistItem.setCreatedAt(wishlistItemEntity.getCreatedAt());
        wishlistItem.setModifiedAt(wishlistItemEntity.getModifiedAt());
        wishlistItem.setItem(ItemDataMapper.transform(wishlistItemEntity.getItem()));

        return wishlistItem;
    }

    public static WishlistItemEntity transform(WishlistItem wishlistItem) {
        WishlistItemEntity wishlistItemEntity = new WishlistItemEntity();

        wishlistItemEntity.setId(wishlistItem.getId());
        wishlistItemEntity.setItemid(wishlistItem.getItemid());
        wishlistItemEntity.setWishlistid(wishlistItem.getWishlistid());
        wishlistItemEntity.setCreatedAt(wishlistItem.getCreatedAt());
        wishlistItemEntity.setModifiedAt(wishlistItem.getModifiedAt());
        wishlistItemEntity.setItem(ItemDataMapper.transform(wishlistItem.getItem()));

        return wishlistItemEntity;
    }

    public static List<WishlistItem> transformWishlistItemEntityList(List<WishlistItemEntity> wishlistItemEntities) {
        List<WishlistItem> wishlistItems = new ArrayList<>();

        for (WishlistItemEntity wishlistItemEntity :
                wishlistItemEntities) {
            wishlistItems.add(WishlistItemDataMapper.transform(wishlistItemEntity));
        }

        return wishlistItems;
    }

    public static List<WishlistItemEntity> transformWishlistItemList(List<WishlistItem> wishlistItems) {
        List<WishlistItemEntity> wishlistItemEntities = new ArrayList<>();

        for (WishlistItem wishlistItem :
                wishlistItems) {
            wishlistItemEntities.add(WishlistItemDataMapper.transform(wishlistItem));
        }

        return wishlistItemEntities;
    }
}

