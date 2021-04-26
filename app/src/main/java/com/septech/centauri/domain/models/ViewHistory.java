package com.septech.centauri.domain.models;

import java.util.List;

public class ViewHistory extends GenericModel {
    private int id;
    private int itemid;
    private int userid;
    private int viewhistoryid;
    private List<Integer> viewHistoryItems;
    private String createdAt;
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

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getViewHistoryId() {
        return viewhistoryid;
    }

    public void setViewHistoryId(int viewhistoryid) {
        this.viewhistoryid = viewhistoryid;
    }

    public List<Integer> getViewHistoryItems() {
        return viewHistoryItems;
    }

    public void setViewHistoryItems(List<Integer> viewHistoryItems) {
        this.viewHistoryItems = viewHistoryItems;
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
