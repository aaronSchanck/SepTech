package com.septech.centauri.data.model.item;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemEntity {

    @SerializedName("id")
    @Expose(serialize = false)
    private Integer id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("quantity")
    @Expose
    private Integer quantity;

    @SerializedName("posted_at")
    @Expose(serialize = false)
    private String postedAt;

    @SerializedName("seller")
    @Expose
    private Integer sellerId;

    @SerializedName("price")
    @Expose
    private String price;

    @SerializedName("can_buy")
    @Expose
    private String canBuy;

    @SerializedName("can_bid")
    @Expose
    private String canBid;

    @SerializedName("highest_bid")
    @Expose(serialize = false)
    private String highestBid;

    @SerializedName("highest_bid_user")
    @Expose(serialize = false)
    private String highestBidUser;

    @SerializedName("bidding_ends")
    @Expose
    private String biddingEnds;

    @SerializedName("quality")
    @Expose
    private String quality;

    @SerializedName("category_id")
    @Expose(serialize = false)
    private String categoryId;

    @SerializedName("category")
    @Expose
    private String category;

    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("item_variants")
    @Expose(serialize = false)
    private String itemVariants;

    @SerializedName("description")
    @Expose
    private String description;

    public ItemEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getPostedAt() {
        return postedAt;
    }

    public void setPostedAt(String postedAt) {
        this.postedAt = postedAt;
    }

    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCanBuy() {
        return canBuy;
    }

    public void setCanBuy(String canBuy) {
        this.canBuy = canBuy;
    }

    public String getCanBid() {
        return canBid;
    }

    public void setCanBid(String canBid) {
        this.canBid = canBid;
    }

    public String getHighestBid() {
        return highestBid;
    }

    public void setHighestBid(String highestBid) {
        this.highestBid = highestBid;
    }

    public String getHighestBidUser() {
        return highestBidUser;
    }

    public void setHighestBidUser(String highestBidUser) {
        this.highestBidUser = highestBidUser;
    }

    public String getBiddingEnds() {
        return biddingEnds;
    }

    public void setBiddingEnds(String biddingEnds) {
        this.biddingEnds = biddingEnds;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getItemVariants() {
        return itemVariants;
    }

    public void setItemVariants(String itemVariants) {
        this.itemVariants = itemVariants;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
