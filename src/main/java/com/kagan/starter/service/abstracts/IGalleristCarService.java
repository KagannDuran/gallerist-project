package com.kagan.starter.service.abstracts;

import com.kagan.starter.dto.gallerist.DtoGallerist;
import com.kagan.starter.dto.gallerist.DtoGalleristIU;
import com.kagan.starter.dto.galleristcar.DtoGalleristCar;
import com.kagan.starter.dto.galleristcar.DtoGalleristCarIU;

import java.util.List;

public interface IGalleristCarService {

    public DtoGalleristCar createGalleristCar(DtoGalleristCarIU dtoGalleristCarIU);
    public List<DtoGalleristCar> allGalleristCar();
    public DtoGalleristCar getByGalleristCar(Long id);
    public DtoGalleristCar updateGalleristCar(DtoGalleristCarIU dtoGalleristCarIU,Long id);
    public void deleteGalleristCar(Long id);
}
