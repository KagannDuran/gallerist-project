package com.kagan.starter.mapper;

import com.kagan.starter.dto.galleristcar.DtoGalleristCar;
import com.kagan.starter.dto.galleristcar.DtoGalleristCarIU;
import com.kagan.starter.entity.GalleristCar;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface GalleristCarMapper {

    //DtoGallerisCarIU den entity dönüşüm
    @Mapping(target = "id",ignore = true)
    @Mapping(target = "createTime",expression = "java(new java.util.Date())")
    GalleristCar dtoGalleristCarIUtoEntity(DtoGalleristCarIU dtoGalleristCarIU);


    //entity den dtoGalleristCar dönüşüm
    DtoGalleristCar entityToGalleristCar(GalleristCar galleristCar);


    //DtoGalleristIU dan gelenleri entitye update
    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateGalleristCar(DtoGalleristCarIU dtoGalleristCarIU, @MappingTarget GalleristCar galleristCar);
}
