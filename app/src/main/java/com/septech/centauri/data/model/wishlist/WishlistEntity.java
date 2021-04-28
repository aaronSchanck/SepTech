package com.septech.centauri.data.model.wishlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.septech.centauri.data.model.wishlistitem.WishlistItemEntity;

import java.util.List;

public class WishlistEntity {
    @SerializedName("id")
    @Expose(serialize = false)
    private int id;

    @SerializedName("userid")
    @Expose()
    private int userid;

    @SerializedName("wishlist_items")
    @Expose(serialize = false)
    private List<WishlistItemEntity> wishlistItems;

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

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public List<WishlistItemEntity> getWishlistItems() {
        return wishlistItems;
    }

    public void setWishlistItems(List<WishlistItemEntity> wishlistItems) {
        this.wishlistItems = wishlistItems;
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
}
