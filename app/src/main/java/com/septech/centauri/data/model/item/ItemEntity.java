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

    @SerializedName("created_at")
    @Expose(serialize = false)
    private String createdAt;

    @SerializedName("updated_at")
    @Expose(serialize = false)
    private String updatedAt;

    @SerializedName("sellerid")
    @Expose
    private Integer sellerId;

    @SerializedName("price")
    @Expose
    private long buyoutPrice;

    @SerializedName("can_buy")
    @Expose
    private Boolean canBuy;

    @SerializedName("can_bid")
    @Expose
    private Boolean canBid;

    @SerializedName("highest_bid")
    @Expose(serialize = false)
    private Long highestBid;

    @SerializedName("highest_bid_user")
    @Expose(serialize = false)
    private Integer highestBidUser;

    @SerializedName("starting_bid")
    @Expose
    private Long startingBid;

    @SerializedName("min_bid_increment")
    @Expose
    private Long minBidIncrement;

    @SerializedName("bidding_ends")
    @Expose
    private String biddingEnds;

    @SerializedName("quality")
    @Expose
    private String quality;

    @SerializedName("category_id")
    @Expose(serialize = false)
    private Integer categoryId;

    @SerializedName("category")
    @Expose
    private CategoryEntity category;

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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    public long getBuyoutPrice() {
        return buyoutPrice;
    }

    public void setBuyoutPrice(long buyoutPrice) {
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

    public Long getHighestBid() {
        return highestBid;
    }

    public void setHighestBid(Long highestBid) {
        this.highestBid = highestBid;
    }

    public Integer getHighestBidUser() {
        return highestBidUser;
    }

    public void setHighestBidUser(Integer highestBidUser) {
        this.highestBidUser = highestBidUser;
    }

    public Long getStartingBid() {
        return startingBid;
    }

    public void setStartingBid(Long startingBid) {
        this.startingBid = startingBid;
    }

    public Long getMinBidIncrement() {
        return minBidIncrement;
    }

    public void setMinBidIncrement(Long minBidIncrement) {
        this.minBidIncrement = minBidIncrement;
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

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
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
