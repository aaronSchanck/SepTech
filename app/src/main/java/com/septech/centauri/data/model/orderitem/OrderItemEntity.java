package com.septech.centauri.data.model.orderitem;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.septech.centauri.data.model.item.ItemEntity;

public class OrderItemEntity {
    @SerializedName("id")
    @Expose(serialize = false)
    private int id;

    @SerializedName("quantity")
    @Expose()
    private int quantity;

    @SerializedName("price")
    @Expose()
    private long price;

    @SerializedName("itemid")
    @Expose()
    private int itemid;

    @SerializedName("orderid")
    @Expose()
    private int orderid;

    @SerializedName("created_at")
    @Expose(serialize = false)
    private String createdAt;

    @SerializedName("modified_at")
    @Expose(serialize = false)
    private String modifiedAt;

    @SerializedName("item")
    @Expose(serialize = false)
    private ItemEntity itemEntity;

    public OrderItemEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public int getItemid() {
        return itemid;
    }

    public void setItemid(int itemid) {
        this.itemid = itemid;
    }

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
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

    public ItemEntity getItemEntity() {
        return itemEntity;
    }

    public void setItemEntity(ItemEntity itemEntity) {
        this.itemEntity = itemEntity;
    }
}
