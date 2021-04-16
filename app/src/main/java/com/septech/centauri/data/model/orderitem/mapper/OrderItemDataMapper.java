package com.septech.centauri.data.model.orderitem.mapper;

import com.septech.centauri.data.model.orderitem.OrderItemEntity;
import com.septech.centauri.domain.models.Order;
import com.septech.centauri.domain.models.OrderItem;

import java.util.ArrayList;
import java.util.List;

public class OrderItemDataMapper {
    public static OrderItem transform(OrderItemEntity orderItemEntity) {
        OrderItem orderItem = new OrderItem();

        orderItem.setId(orderItemEntity.getId());
        orderItem.setQuantity(orderItemEntity.getQuantity());
        orderItem.setPrice(orderItemEntity.getPrice());
        orderItem.setItemid(orderItemEntity.getItemid());
        orderItem.setOrderid(orderItemEntity.getOrderid());
        orderItem.setCreatedAt(orderItemEntity.getCreatedAt());
        orderItem.setModifiedAt(orderItemEntity.getModifiedAt());

        return orderItem;
    }

    public static OrderItemEntity transform(OrderItem orderItem) {
        OrderItemEntity orderItemEntity = new OrderItemEntity();

        orderItemEntity.setId(orderItem.getId());
        orderItemEntity.setQuantity(orderItem.getQuantity());
        orderItemEntity.setPrice(orderItem.getPrice());
        orderItemEntity.setItemid(orderItem.getItemid());
        orderItemEntity.setOrderid(orderItem.getOrderid());
        orderItemEntity.setCreatedAt(orderItem.getCreatedAt());
        orderItemEntity.setModifiedAt(orderItem.getModifiedAt());

        return orderItemEntity;
    }

    public static List<OrderItem> transformOrderItemEntityList(List<OrderItemEntity> orderItemEntityList) {
        List<OrderItem> orderItemList = new ArrayList<>();

        for (OrderItemEntity orderItem :
                orderItemEntityList) {
            orderItemList.add(OrderItemDataMapper.transform(orderItem));
        }

        return orderItemList;
    }

    public static List<OrderItemEntity> transformOrderItemList(List<OrderItem> orderItemList) {
        List<OrderItemEntity> orderItemEntityList = new ArrayList<>();

        for (OrderItem orderItem :
                orderItemList) {
            orderItemEntityList.add(OrderItemDataMapper.transform(orderItem));
        }

        return orderItemEntityList;
    }
}
