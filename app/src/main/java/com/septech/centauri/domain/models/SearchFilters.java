package com.septech.centauri.domain.models;


import com.septech.centauri.ui.user.search.SearchSortType;
import com.septech.centauri.ui.user.search.SearchViewType;

public class SearchFilters {
    private SearchViewType mSearchViewType;
    private SearchSortType mSortType;

    private float mLeftSlider;
    private float mRightSlider;

    private boolean mBuyNow;
    private boolean mAuction;
    private boolean mAuctionEnding;

    private float mMinimumSellerRating;
    private float mMinimumItemRating;

    public SearchFilters(SearchViewType searchViewType, SearchSortType sortType, float leftSlider,
                         float rightSlider, boolean buyNow, boolean auction, boolean auctionEnding,
                         float minimumSellerRating, float minimumItemRating) {
        mSearchViewType = searchViewType;
        mSortType = sortType;
        mLeftSlider = leftSlider;
        mRightSlider = rightSlider;
        mBuyNow = buyNow;
        mAuction = auction;
        mAuctionEnding = auctionEnding;
        mMinimumSellerRating = minimumSellerRating;
        mMinimumItemRating = minimumItemRating;
    }

    public SearchViewType getSearchViewType() {
        return mSearchViewType;
    }

    public void setSearchViewType(SearchViewType searchViewType) {
        mSearchViewType = searchViewType;
    }

    public SearchSortType getSortType() {
        return mSortType;
    }

    public void setSortType(SearchSortType sortType) {
        mSortType = sortType;
    }

    public float getLeftSlider() {
        return mLeftSlider;
    }

    public void setLeftSlider(float leftSlider) {
        mLeftSlider = leftSlider;
    }

    public float getRightSlider() {
        return mRightSlider;
    }

    public void setRightSlider(float rightSlider) {
        mRightSlider = rightSlider;
    }

    public boolean isBuyNow() {
        return mBuyNow;
    }

    public void setBuyNow(boolean buyNow) {
        mBuyNow = buyNow;
    }

    public boolean isAuction() {
        return mAuction;
    }

    public void setAuction(boolean auction) {
        mAuction = auction;
    }

    public boolean isAuctionEnding() {
        return mAuctionEnding;
    }

    public void setAuctionEnding(boolean auctionEnding) {
        mAuctionEnding = auctionEnding;
    }

    public float getMinimumSellerRating() {
        return mMinimumSellerRating;
    }

    public void setMinimumSellerRating(float minimumSellerRating) {
        mMinimumSellerRating = minimumSellerRating;
    }

    public float getMinimumItemRating() {
        return mMinimumItemRating;
    }

    public void setMinimumItemRating(float minimumItemRating) {
        mMinimumItemRating = minimumItemRating;
    }
}
