package com.septech.centauri.data.model.wishlistitem;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.septech.centauri.data.model.item.ItemEntity;
import com.septech.centauri.domain.models.Item;

public class WishlistItemEntity {

    @SerializedName("id")
    @Expose(serialize = false)
    private int id;

    @SerializedName("itemid")
    @Expose()
    private int itemid;

    @SerializedName("item")
    @Expose(serialize = false)
    private ItemEntity item;

    @SerializedName("wishlistid")
    @Expose()
    private int wishlistid;

    @SerializedName("created_at")
    @Expose(serialize = false)
    private String createdAt;

    @SerializedName("modified_at")
    @Expose(serialize = false)
    private String modifiedAt;

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

    public ItemEntity getItem() {
        return item;
    }

    public void setItem(ItemEntity item) {
        this.item = item;
    }
}
