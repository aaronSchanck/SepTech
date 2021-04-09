package com.septech.centauri.data.model.item.mapper;

import com.septech.centauri.data.model.category.mapper.CategoryDataMapper;
import com.septech.centauri.data.model.item.ItemEntity;
import com.septech.centauri.domain.models.Item;

public class ItemDataMapper {
    public static Item transform(ItemEntity itemEntity) {
        Item item = new Item();

        return item;
    }

    public static ItemEntity transform(Item item) {
        ItemEntity itemEntity = new ItemEntity();

        itemEntity.setId(item.getId());
        itemEntity.setName(item.getName());
        itemEntity.setQuantity(item.getQuantity());
//        itemEntity.setPostedAt(item.getPostedAt());
        itemEntity.setSellerId(item.getSellerId());
        itemEntity.setBuyoutPrice(item.getBuyoutPrice());
        itemEntity.setCanBuy(item.isCanBuy());
        itemEntity.setCanBid(item.isCanBid());
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
}
