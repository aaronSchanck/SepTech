package com.septech.centauri.data.model.order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.septech.centauri.data.model.orderitem.OrderItemEntity;
import com.septech.centauri.domain.models.OrderItem;
import com.septech.centauri.domain.models.User;

import java.util.List;

public class OrderEntity {
    @SerializedName("id")
    @Expose(serialize = false)
    private int id;

    @SerializedName("userid")
    @Expose()
    private int userid;

    @SerializedName("ordered")
    @Expose()
    private boolean ordered;

    @SerializedName("date_created")
    @Expose(serialize = false)
    private String dateCreated;

    @SerializedName("date_shipped")
    @Expose(serialize = false)
    private String dateShipped;

    @SerializedName("date_delivered")
    @Expose(serialize = false)
    private String dateDelivered;

    @SerializedName("order_items")
    @Expose(serialize = false)
    private List<OrderItemEntity> orderItems;

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

    public boolean isOrdered() {
        return ordered;
    }

    public void setOrdered(boolean ordered) {
        this.ordered = ordered;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDateShipped() {
        return dateShipped;
    }

    public void setDateShipped(String dateShipped) {
        this.dateShipped = dateShipped;
    }

    public String getDateDelivered() {
        return dateDelivered;
    }

    public void setDateDelivered(String dateDelivered) {
        this.dateDelivered = dateDelivered;
    }

    public List<OrderItemEntity> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemEntity> orderItems) {
        this.orderItems = orderItems;
    }
}
