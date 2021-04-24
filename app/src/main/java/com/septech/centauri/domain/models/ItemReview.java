package com.septech.centauri.domain.models;

public class ItemReview extends GenericModel {
    private int id;

    private int userid;
    private User user;

    private int itemid;

    private Float rating;

    private String content;

    private String createdAt;
    private String modifiedAt;

    public ItemReview(int userid, int itemid, Float rating, String content) {
        this.userid = userid;
        this.itemid = itemid;
        this.rating = rating;
        this.content = content;
    }

    public ItemReview() {

    }

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getItemid() {
        return itemid;
    }

    public void setItemid(int itemid) {
        this.itemid = itemid;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    @Override
    public void initTestData() {

    }
}
