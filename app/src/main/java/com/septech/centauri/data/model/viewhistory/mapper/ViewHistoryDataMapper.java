package com.septech.centauri.data.model.viewhistory.mapper;

import com.septech.centauri.data.model.viewhistory.ViewHistoryEntity;
import com.septech.centauri.domain.models.ViewHistory;

public class ViewHistoryDataMapper {
    public static ViewHistory transform(ViewHistoryEntity viewHistoryEntity) {
        ViewHistory viewhistory = new ViewHistory();

        viewhistory.setId(viewHistoryEntity.getId());
        viewhistory.setUserid(viewHistoryEntity.getUserid());
        viewhistory.setViewHistoryItems(viewHistoryEntity.getViewHistoryItems());
        viewhistory.setCreatedAt(viewHistoryEntity.getCreatedAt());
        viewhistory.setModifiedAt(viewHistoryEntity.getModifiedAt());

        return viewhistory;
    }

    public static ViewHistoryEntity transform(ViewHistory viewhistory) {
        ViewHistoryEntity viewHistoryEntity = new ViewHistoryEntity();

        viewHistoryEntity.setId(viewhistory.getId());
        viewHistoryEntity.setUserid(viewhistory.getUserid());
        viewHistoryEntity.setViewHistoryItems(viewhistory.getViewHistoryItems());
        viewHistoryEntity.setCreatedAt(viewhistory.getCreatedAt());
        viewHistoryEntity.setModifiedAt(viewhistory.getModifiedAt());

        return viewHistoryEntity;
    }
}
