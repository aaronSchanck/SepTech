package com.septech.centauri.domain.models;

import com.septech.centauri.data.model.orderitem.OrderItemEntity;

import java.util.List;

public class Order {
    private int id;
    private int userid;
    private boolean ordered;
    private String dateCreated;
    private String dateShipped;
    private String dateDelivered;
    private List<OrderItem> orderItems;

    public Order() {
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

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}

