package com.kagan.starter.mapper;

import com.kagan.starter.dto.car.DtoCar;
import com.kagan.starter.dto.car.DtoCarIU;
import com.kagan.starter.entity.Car;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CarMapper {

    //DtoCarIU dan entity dönüşümü
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTime", expression = "java(new java.util.Date())")
    Car dtoCarIUtoEntity(DtoCarIU dtoCarIU);

    //Entity den DtouCar dönüşümü
    DtoCar entityToDtoCar (Car car);

    //DtoCar dan entity dönüşümü
    Car dtocarToEntity (DtoCar dtoCar);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTime", ignore = true) // createTime değişmemeli
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)//entity de null olan alanları atlar
    void updateDtoCarIUtoCar (DtoCarIU dtoCarIU, @MappingTarget Car car);
}
