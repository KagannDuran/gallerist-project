package com.kagan.starter.service.abstracts;

import com.kagan.starter.dto.saledCar.DtoSaledCar;
import com.kagan.starter.dto.saledCar.DtoSaledCarIU;

import java.util.List;

public interface ISaledCarService {

    public DtoSaledCar buyCar(DtoSaledCarIU dtoSaledCarIU);
    public List<DtoSaledCar> allSaledCar();
    public DtoSaledCar saledCarById(Long id);

}
