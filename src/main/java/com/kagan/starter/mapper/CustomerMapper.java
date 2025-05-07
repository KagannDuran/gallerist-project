package com.kagan.starter.mapper;

import com.kagan.starter.dto.customer.DtoCustomer;
import com.kagan.starter.dto.customer.DtoCustomerIU;
import com.kagan.starter.entity.Account;
import com.kagan.starter.entity.Address;
import com.kagan.starter.entity.Customer;
import org.mapstruct.*;


@Mapper(componentModel = "spring")
public interface  CustomerMapper {



    /**
     * DtoCustomerIU → Customer
     * addressId ve accountId'den ilgili entity'ler resolve edilir
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTime", expression = "java(new java.util.Date())")
    Customer iuToEntity(DtoCustomerIU dto);


    DtoCustomer toDto(Customer customer);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTime", ignore = true) // createTime değişmemeli
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)//entity de null olan alanları atlar
    void updateCustomerFromDto(DtoCustomerIU dto, @MappingTarget Customer customer);


}
