package com.septech.centauri.data.model.item.mapper;

import com.septech.centauri.data.model.category.mapper.CategoryDataMapper;
import com.septech.centauri.data.model.item.ItemEntity;
import com.septech.centauri.domain.models.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemDataMapper {
    public static Item transform(ItemEntity itemEntity) {
        Item item = new Item();

        item.setId(itemEntity.getId());
        item.setName(itemEntity.getName());
        item.setQuantity(itemEntity.getQuantity());
        item.setCreatedAt(itemEntity.getCreatedAt());
        item.setUpdatedAt(itemEntity.getUpdatedAt());
        item.setSellerId(itemEntity.getSellerId());
        item.setBuyoutPrice(itemEntity.getBuyoutPrice());
        item.setCanBuy(itemEntity.getCanBuy());
        item.setCanBid(itemEntity.getCanBid());
        item.setHighestBid(itemEntity.getHighestBid());
        item.setHighestBidUser(itemEntity.getHighestBidUser());
        item.setBiddingEnds(itemEntity.getBiddingEnds());
        item.setStartingBid(itemEntity.getStartingBid());
        item.setMinBidIncrement(itemEntity.getMinBidIncrement());
        item.setQuality(itemEntity.getQuality());
        item.setCategory(CategoryDataMapper.transform(itemEntity.getCategory()));
        item.setDescription(itemEntity.getDescription());

        return item;
    }

    public static ItemEntity transform(Item item) {
        ItemEntity itemEntity = new ItemEntity();

        itemEntity.setId(item.getId());
        itemEntity.setName(item.getName());
        itemEntity.setQuantity(item.getQuantity());
        itemEntity.setCreatedAt(item.getCreatedAt());
        itemEntity.setUpdatedAt(item.getUpdatedAt());
        itemEntity.setSellerId(item.getSellerId());
        itemEntity.setBuyoutPrice(item.getBuyoutPrice());
        itemEntity.setCanBuy(item.getCanBuy());
        itemEntity.setCanBid(item.getCanBid());
        itemEntity.setHighestBid(item.getHighestBid());
        itemEntity.setHighestBidUser(item.getHighestBidUser());
        itemEntity.setBiddingEnds(item.getBiddingEnds());
        itemEntity.setStartingBid(item.getStartingBid());
        itemEntity.setMinBidIncrement(item.getMinBidIncrement());
        itemEntity.setQuality(item.getQuality());
        itemEntity.setCategory(CategoryDataMapper.transform(item.getCategory()));
        itemEntity.setDescription(item.getDescription());

        return itemEntity;
    }

    public static List<Item> transformItemEntityList(List<ItemEntity> itemEntityList) {
        List<Item> items = new ArrayList<>();

        for (ItemEntity i : itemEntityList) {
            items.add(ItemDataMapper.transform(i));
        }

        return items;
    }

    public static List<ItemEntity> transformItemList(List<Item> itemList) {
        List<ItemEntity> items = new ArrayList<>();

        for (Item i : itemList) {
            items.add(ItemDataMapper.transform(i));
        }

        return items;
    }
}
