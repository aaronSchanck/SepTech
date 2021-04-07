package com.septech.centauri.data.model.address.mapper;

import com.septech.centauri.data.model.address.AddressEntity;
import com.septech.centauri.domain.models.Address;

public class AddressDataMapper {
    public static AddressEntity transform(Address address) {
        AddressEntity addressEntity = new AddressEntity();



        return addressEntity;
    }

    public static Address transform(AddressEntity addressEntity) {
        Address address = new Address();



        return address;
    }
}
