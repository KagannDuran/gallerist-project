package com.kagan.starter.mapper;

import com.kagan.starter.dto.customer.DtoCustomer;
import com.kagan.starter.dto.customer.DtoCustomerIU;
import com.kagan.starter.dto.saledCar.DtoSaledCar;
import com.kagan.starter.dto.saledCar.DtoSaledCarIU;
import com.kagan.starter.entity.Customer;
import com.kagan.starter.entity.SaledCar;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SaledCarMapper {

    /**
     * DtoCustomerIU → Customer
     * addressId ve accountId'den ilgili entity'ler resolve edilir
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTime", expression = "java(new java.util.Date())")
    SaledCar iuToEntity(DtoSaledCarIU dtoSaledCarIU);

    DtoSaledCar entitytoDto(SaledCar saledCar);

}
