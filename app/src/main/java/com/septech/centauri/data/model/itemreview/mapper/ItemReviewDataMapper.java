package com.septech.centauri.data.model.itemreview.mapper;

import com.septech.centauri.data.model.itemreview.ItemReviewEntity;
import com.septech.centauri.domain.models.ItemReview;

import java.util.ArrayList;
import java.util.List;

public class ItemReviewDataMapper {
    public static ItemReview transform(ItemReviewEntity itemReviewEntity) {
        ItemReview itemReview = new ItemReview();

        itemReview.setId(itemReviewEntity.getId());
        itemReview.setUserid(itemReviewEntity.getUserid());
        itemReview.setItemid(itemReviewEntity.getItemid());
        itemReview.setRating(itemReviewEntity.getRating());
        itemReview.setContent(itemReviewEntity.getContent());
        itemReview.setCreatedAt(itemReviewEntity.getCreatedAt());
        itemReview.setModifiedAt(itemReviewEntity.getModifiedAt());

        return itemReview;
    }

    public static ItemReviewEntity transform(ItemReview itemReview) {
        ItemReviewEntity itemReviewEntity = new ItemReviewEntity();

        itemReviewEntity.setId(itemReview.getId());
        itemReviewEntity.setUserid(itemReview.getUserid());
        itemReviewEntity.setItemid(itemReview.getItemid());
        itemReviewEntity.setRating(itemReview.getRating());
        itemReviewEntity.setContent(itemReview.getContent());
        itemReviewEntity.setCreatedAt(itemReview.getCreatedAt());
        itemReviewEntity.setModifiedAt(itemReview.getModifiedAt());

        return itemReviewEntity;
    }

    public static List<ItemReview> transformItemReviewEntityList(List<ItemReviewEntity> itemReviewEntityList) {
        List<ItemReview> itemReviewList = new ArrayList<>();

        for (ItemReviewEntity review :
                itemReviewEntityList) {
            itemReviewList.add(ItemReviewDataMapper.transform(review));
        }

        return itemReviewList;
    }

    public static List<ItemReviewEntity> transformItemReviewList(List<ItemReview> itemReviewList) {
        List<ItemReviewEntity> itemReviewEntityList = new ArrayList<>();

        for (ItemReview review :
                itemReviewList) {
            itemReviewEntityList.add(ItemReviewDataMapper.transform(review));
        }

        return itemReviewEntityList;
    }
}
