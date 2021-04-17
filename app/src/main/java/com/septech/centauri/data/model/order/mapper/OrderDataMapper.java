package com.septech.centauri.data.model.order.mapper;

import com.septech.centauri.data.model.order.OrderEntity;
import com.septech.centauri.data.model.orderitem.OrderItemEntity;
import com.septech.centauri.data.model.orderitem.mapper.OrderItemDataMapper;
import com.septech.centauri.domain.models.Order;

import java.util.List;

public class OrderDataMapper {

    public static Order transform(OrderEntity orderEntity) {
        Order order = new Order();

        order.setId(orderEntity.getId());
        order.setUserid(orderEntity.getUserid());
        order.setOrdered(orderEntity.isOrdered());
        order.setDateCreated(orderEntity.getDateCreated());
        order.setDateShipped(orderEntity.getDateShipped());
        order.setDateDelivered(orderEntity.getDateDelivered());

        order.setOrderItems(OrderItemDataMapper.transformOrderItemEntityList(orderEntity.getOrderItems()));

        return order;
    }

    public static OrderEntity transform(Order order) {
        OrderEntity orderEntity = new OrderEntity();

        orderEntity.setId(order.getId());
        orderEntity.setUserid(order.getUserid());
        orderEntity.setOrdered(order.isOrdered());
        orderEntity.setDateCreated(order.getDateCreated());
        orderEntity.setDateShipped(order.getDateShipped());
        orderEntity.setDateDelivered(order.getDateDelivered());

        orderEntity.setOrderItems(OrderItemDataMapper.transformOrderItemList(order.getOrderItems()));

        return orderEntity;
    }
}
