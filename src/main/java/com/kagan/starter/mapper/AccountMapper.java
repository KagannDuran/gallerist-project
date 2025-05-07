package com.kagan.starter.mapper;


import com.kagan.starter.dto.account.AccountDto;
import com.kagan.starter.dto.account.AccountDtoIU;
import com.kagan.starter.entity.Account;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AccountMapper {

     //AccountDtoIU dan entity e dönüşüm
     @Mapping(target = "createTime", expression = "java(new java.util.Date())")
    Account AccountDtoIUtoEntity(AccountDtoIU accountDtoIU);

    //Entity den AccountDto dönüşüm
    AccountDto EntitytoAccountDto(Account account);

    //AccountDto dan entity dönüşüm
    Account AccountDtotoEntity(AccountDto accountDto);


    @Mapping(target = "createTime", ignore = true) // createTime değişmemeli
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)//entity de null olan alanları atlar
    void updateAccountFromDto(AccountDtoIU dto, @MappingTarget Account entity);
}
