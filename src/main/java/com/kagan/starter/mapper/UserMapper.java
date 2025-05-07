package com.kagan.starter.mapper;

import com.kagan.starter.dto.UserDto;
import com.kagan.starter.dto.UserRegisterDto;
import com.kagan.starter.entity.AppUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);


    // RegisterDto'dan Entity'e dönüşüm
    @Mapping(target = "createTime", expression = "java(new java.util.Date())")
    @Mapping(source = "password", target = "password", qualifiedByName = "encodePassword")
    AppUser RegistertoEntity(UserRegisterDto dto);

    // Şifre encode eden method
    @Named("encodePassword")
    static String encodePassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    //Entity'den UserDto dönüşüm
    UserDto toDtoUser(AppUser user);
}
