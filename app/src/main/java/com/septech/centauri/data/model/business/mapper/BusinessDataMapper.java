package com.septech.centauri.data.model.business.mapper;

import com.septech.centauri.data.model.business.BusinessEntity;
import com.septech.centauri.domain.models.Business;

public class BusinessDataMapper {

    public static BusinessEntity transform(Business business) {
        BusinessEntity businessEntity = new BusinessEntity();

        return businessEntity;
    }

    public static Business transform(BusinessEntity businessEntity) {
        Business business = new Business();

        return business;
    }
}
