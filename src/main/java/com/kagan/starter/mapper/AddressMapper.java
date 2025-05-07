package com.kagan.starter.mapper;

import com.kagan.starter.dto.address.DtoAddress;
import com.kagan.starter.dto.address.DtoAddressUI;
import com.kagan.starter.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    // AdressDtoIU'dan Entity'e dönüşüm
    @Mapping(target = "createTime", expression = "java(new java.util.Date())")
    Address AddressDtoIUtoEntity(DtoAddressUI dtoAddressUI);

    //Entity den AddressDto ya dönüşüm
    DtoAddress EntitytoDtoAddress(Address address);

    //AddressDto dan Entity dönüşüm
    Address DtoAddresstoEntity(DtoAddress dtoAddress);
}
