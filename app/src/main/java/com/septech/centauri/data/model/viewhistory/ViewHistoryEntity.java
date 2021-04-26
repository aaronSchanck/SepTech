package com.septech.centauri.data.model.viewhistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ViewHistoryEntity {
    @SerializedName("id")
    @Expose(serialize = false)
    private int id;

    @SerializedName("userid")
    @Expose()
    private int userid;

    @SerializedName("view_history_items")
    @Expose(serialize = false)
    private List<Integer> viewHistoryItems;

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
}
