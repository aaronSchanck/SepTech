package com.septech.centauri.data.model.business.mapper;

import com.septech.centauri.data.model.business.BusinessEntity;
import com.septech.centauri.domain.models.Business;

public class BusinessDataMapper {

    public static BusinessEntity transform(Business business) {
        BusinessEntity businessEntity = new BusinessEntity();

        businessEntity.setId(business.getId());
        businessEntity.setEmail(business.getEmail());
        businessEntity.setPassword(business.getPassword());
        businessEntity.setOwnerFullName(business.getOwnerFullName());
        businessEntity.setBusinessName(business.getBusinessName());
        businessEntity.setPhoneNumber(business.getPhoneNumber());
        businessEntity.setPasswordSalt(business.getPasswordSalt());
        businessEntity.setCreatedAt(business.getCreatedAt());
        businessEntity.setModifiedAt(business.getCreatedAt());
        businessEntity.setPasswordResetCode(business.getCreatedAt());
        businessEntity.setPasswordResetTimeout(business.getCreatedAt());

        return businessEntity;
    }

    public static Business transform(BusinessEntity businessEntity) {
        Business business = new Business();

        business.setId(businessEntity.getId());
        business.setEmail(businessEntity.getEmail());
        business.setPassword(businessEntity.getPassword());
        business.setOwnerFullName(businessEntity.getOwnerFullName());
        business.setBusinessName(businessEntity.getBusinessName());
        business.setPhoneNumber(businessEntity.getPhoneNumber());
        business.setPasswordSalt(businessEntity.getPasswordSalt());
        business.setCreatedAt(businessEntity.getCreatedAt());
        business.setModifiedAt(businessEntity.getCreatedAt());
        business.setPasswordResetCode(businessEntity.getCreatedAt());
        business.setPasswordResetTimeout(businessEntity.getCreatedAt());

        return business;
    }
}
