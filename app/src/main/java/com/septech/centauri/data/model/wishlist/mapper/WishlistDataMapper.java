package com.septech.centauri.data.model.wishlist.mapper;

import com.septech.centauri.data.model.wishlist.WishlistEntity;
import com.septech.centauri.data.model.wishlistitem.mapper.WishlistItemDataMapper;
import com.septech.centauri.domain.models.Wishlist;

public class WishlistDataMapper {
    public static Wishlist transform(WishlistEntity wishlistEntity) {
        Wishlist wishlist = new Wishlist();

        wishlist.setId(wishlistEntity.getId());
        wishlist.setUserid(wishlistEntity.getUserid());
        wishlist.setWishlistItems(WishlistItemDataMapper.transformWishlistItemEntityList(wishlistEntity.getWishlistItems()));
        wishlist.setCreatedAt(wishlistEntity.getCreatedAt());
        wishlist.setModifiedAt(wishlistEntity.getModifiedAt());

        return wishlist;
    }

    public static WishlistEntity transform(Wishlist wishlist) {
        WishlistEntity wishlistEntity = new WishlistEntity();

        wishlistEntity.setId(wishlist.getId());
        wishlistEntity.setUserid(wishlist.getUserid());
        wishlistEntity.setWishlistItems(WishlistItemDataMapper.transformWishlistItemList(wishlist.getWishlistItems()));
        wishlistEntity.setCreatedAt(wishlist.getCreatedAt());
        wishlistEntity.setModifiedAt(wishlist.getModifiedAt());

        return wishlistEntity;
    }
}
