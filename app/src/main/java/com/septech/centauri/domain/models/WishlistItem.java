package com.septech.centauri.domain.models;

import androidx.annotation.NonNull;

public class WishlistItem extends GenericModel {
    private int id;
    private int itemid;
    private Item item;
    private int wishlistid;
    private String createdAt;
    private String modifiedAt;

    public WishlistItem(int id) {
        super(id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getItemid() {
        return itemid;
    }

    public void setItemid(int itemid) {
        this.itemid = itemid;
    }

    public int getWishlistid() {
        return wishlistid;
    }

    public void setWishlistid(int wishlistid) {
        this.wishlistid = wishlistid;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(String modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @Override
    public void initTestData() {

    }

    @NonNull
    @Override
    public String toString() {
        return "WishlistItem " + super.toString();
    }
}
