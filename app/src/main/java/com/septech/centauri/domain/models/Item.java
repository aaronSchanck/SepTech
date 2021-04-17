package com.septech.centauri.domain.models;

import java.math.BigDecimal;

public class Item {
    private Integer id;
    private String name;
    private Integer quantity;
    private String createdAt;
    private String updatedAt;
    private Integer sellerId;
    private Long buyoutPrice;
    private Boolean canBuy;

    private Boolean canBid;
    private Long highestBid;
    private Integer highestBidUser;
    private String biddingEnds;
    private Long startingBid;
    private Long minBidIncrement;

    private String quality;
    private Integer categoryId;
    private Category category;

    private String itemVariants;
    private String description;

    public Item() {
        //left empty
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

    public Long getBuyoutPrice() {
        return buyoutPrice;
    }

    public void setBuyoutPrice(Long buyoutPrice) {
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

    public String getBiddingEnds() {
        return biddingEnds;
    }

    public void setBiddingEnds(String biddingEnds) {
        this.biddingEnds = biddingEnds;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
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

    public BigDecimal getBigDecDollarPrice() {
        return new BigDecimal(getStringDollarPrice());
    }

    public String getStringDollarPrice() {
        String amount;
        if (buyoutPrice != null) {
            amount = String.valueOf(buyoutPrice);
        } else {
            amount = String.valueOf(highestBid);
        }

        return amount.substring(0, amount.length() - 2)
                + "."
                + amount.substring(amount.length() - 2);
    }

    public String getDisplayablePrice() {
        return "$" + getStringDollarPrice();
    }
}
