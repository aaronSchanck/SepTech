package com.septech.centauri.data.model.orderitem;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderItemEntity {
    @SerializedName("id")
    @Expose(serialize = false)
    private int id;

    @SerializedName("quantity")
    @Expose()
    private int quantity;

    @SerializedName("price")
    @Expose()
    private float price;

    @SerializedName("itemid")
    @Expose()
    private int itemid;

    @SerializedName("orderid")
    @Expose()
    private int orderid;

    @SerializedName("added_at")
    @Expose(serialize = false)
    private String addedAt;

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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
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

    public String getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(String addedAt) {
        this.addedAt = addedAt;
    }
}
