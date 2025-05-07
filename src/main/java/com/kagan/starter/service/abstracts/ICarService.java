package com.kagan.starter.service.abstracts;

import com.kagan.starter.dto.car.DtoCar;
import com.kagan.starter.dto.car.DtoCarIU;

import java.util.List;

public interface ICarService {

    public DtoCar createCar(DtoCarIU dtoCarIU);
    public List<DtoCar> getAllCar();
    public DtoCar getByCarId(Long id);
    public DtoCar updateCar(DtoCarIU dtoCarIU,Long id);
    public void deleteCar(Long id);

}
