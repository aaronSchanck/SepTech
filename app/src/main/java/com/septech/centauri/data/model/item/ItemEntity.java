package com.septech.centauri.data.model.item;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.septech.centauri.data.model.category.CategoryEntity;

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
    private String buyoutPrice;

    @SerializedName("can_buy")
    @Expose
    private Boolean canBuy;

    @SerializedName("can_bid")
    @Expose
    private Boolean canBid;

    @SerializedName("highest_bid")
    @Expose(serialize = false)
    private String highestBid;

    @SerializedName("highest_bid_user")
    @Expose(serialize = false)
    private String highestBidUser;

    @SerializedName("starting_bid")
    @Expose
    private String startingBid;

    @SerializedName("min_bid_increment")
    @Expose
    private String minBidIncrement;

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
    private CategoryEntity category;

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

    public String getBuyoutPrice() {
        return buyoutPrice;
    }

    public void setBuyoutPrice(String buyoutPrice) {
        this.buyoutPrice = buyoutPrice;
    }

    public Boolean getCanBuy() {
        return canBuy;
    }

    public void setCanBuy(Boolean canBuy) {
        this.canBuy = canBuy;
    }

    public Boolean getCanBid() {
        return canBid;
    }

    public void setCanBid(Boolean canBid) {
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

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
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

    public String getStartingBid() {
        return startingBid;
    }

    public void setStartingBid(String startingBid) {
        this.startingBid = startingBid;
    }

    public String getMinBidIncrement() {
        return minBidIncrement;
    }

    public void setMinBidIncrement(String minBidIncrement) {
        this.minBidIncrement = minBidIncrement;
    }
}
