package com.septech.centauri.data.model.wishlist.mapper;

import com.septech.centauri.data.model.wishlist.WishlistEntity;
import com.septech.centauri.domain.models.Wishlist;

public class WishlistDataMapper {
    public static Wishlist transform(WishlistEntity wishlistEntity) {
        Wishlist wishlist = new Wishlist();

        return wishlist;
    }

    public static WishlistEntity transform(Wishlist wishlist) {
        WishlistEntity wishlistEntity = new WishlistEntity();

        return wishlistEntity;
    }
}
