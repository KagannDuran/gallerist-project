package com.kagan.starter.mapper;

import com.kagan.starter.dto.gallerist.DtoGallerist;
import com.kagan.starter.dto.gallerist.DtoGalleristIU;
import com.kagan.starter.entity.Gallerist;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface GalleristMapper {

    //DtoGalleristIU dan entity e dönüşüm
    @Mapping(target = "id",ignore = true)
    @Mapping(target = "createTime",expression = "java(new java.util.Date())")
    Gallerist dtoGalleristIUtoEntity(DtoGalleristIU dtoGalleristIU);

    //entity den dtoGallerist e dönüşüm
    DtoGallerist entityToDtoGallerist(Gallerist gallerist);

    //entity den dtoGallerist e dönüşüm
    Gallerist DtoGalleristToEntity(DtoGallerist dtoGallerist);


    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)//entity de null olan alanları atlar
    void updateGalleristFromDto(DtoGalleristIU dtoGalleristIU, @MappingTarget Gallerist gallerist);
}
