package com.septech.centauri.data.model.order.mapper;

import com.septech.centauri.data.model.order.OrderEntity;
import com.septech.centauri.domain.models.Order;

public class OrderDataMapper {

    public static Order transform(OrderEntity orderEntity) {
        Order order = new Order();

        order.setId(orderEntity.getId());

        return order;
    }

    public static OrderEntity transform(Order order) {
        OrderEntity orderEntity = new OrderEntity();

        orderEntity.setId(order.getId());

        return orderEntity;
    }
}
