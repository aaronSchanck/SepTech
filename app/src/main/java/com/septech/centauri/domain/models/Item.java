package com.septech.centauri.domain.models;

import androidx.annotation.NonNull;

import java.math.BigDecimal;
import java.util.List;

public class Item extends GenericModel {
    private Integer id;
    private String name;
    private Integer quantity;
    private String createdAt;
    private String updatedAt;

    private Integer sellerId;
    private Business seller;

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

    private List<ItemReview> reviews;

    public Item() {
        //left empty
    }

    public Item(String name, Integer quantity, Integer sellerId, Long buyoutPrice, Boolean canBuy, Boolean canBid, String biddingEnds, Long startingBid, Long minBidIncrement, String quality, String description) {
        this.name = name;
        this.quantity = quantity;
        this.quality = quality;

        this.sellerId = sellerId;

        this.canBuy = canBuy;
        this.buyoutPrice = buyoutPrice;

        this.canBid = canBid;
        this.biddingEnds = biddingEnds;
        this.startingBid = startingBid;
        this.minBidIncrement = minBidIncrement;

        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        super.setId(id);
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

    public Business getSeller() {
        return seller;
    }

    public void setSeller(Business seller) {
        this.seller = seller;
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
        String dollarPrice = getStringDollarPrice();
        if (dollarPrice.charAt(0) == '.') {
            dollarPrice = "0" + dollarPrice;
        }

        return "$" + dollarPrice;
    }

    public List<ItemReview> getReviews() {
        return reviews;
    }

    public void setReviews(List<ItemReview> reviews) {
        this.reviews = reviews;
    }

    public float getAverageRating() {
        if (getReviews().size() == 0) return 0f;

        float sum = 0f;

        for (ItemReview review :
                getReviews()) {
            sum += review.getRating();
        }

        return sum / getReviews().size();
    }

    @Override
    public void initTestData() {

    }

    @NonNull
    @Override
    public String toString() {
        return "Item " + super.toString();
    }
}
