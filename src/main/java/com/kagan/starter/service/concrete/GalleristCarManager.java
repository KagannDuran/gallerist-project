package com.kagan.starter.service.concrete;

import com.kagan.starter.dto.car.DtoCar;
import com.kagan.starter.dto.gallerist.DtoGallerist;
import com.kagan.starter.dto.gallerist.DtoGalleristIU;
import com.kagan.starter.dto.galleristcar.DtoGalleristCar;
import com.kagan.starter.dto.galleristcar.DtoGalleristCarIU;
import com.kagan.starter.entity.Car;
import com.kagan.starter.entity.Gallerist;
import com.kagan.starter.entity.GalleristCar;
import com.kagan.starter.exception.BaseException;
import com.kagan.starter.exception.ErrorMessage;
import com.kagan.starter.exception.MessageType;
import com.kagan.starter.mapper.CarMapper;
import com.kagan.starter.mapper.GalleristCarMapper;
import com.kagan.starter.mapper.GalleristMapper;
import com.kagan.starter.repository.CarRepository;
import com.kagan.starter.repository.GalleristCarRepository;
import com.kagan.starter.repository.GalleristRepository;
import com.kagan.starter.service.abstracts.ICarService;
import com.kagan.starter.service.abstracts.IGalleristCarService;
import com.kagan.starter.service.abstracts.IGalleristService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GalleristCarManager implements IGalleristCarService {

    private final GalleristCarRepository galleristCarRepository;
    private final GalleristRepository galleristRepository;
    private final CarRepository carRepository;
    private final GalleristCarMapper galleristCarMapper;


    public GalleristCarManager(GalleristCarRepository galleristCarRepository, GalleristRepository galleristRepository, CarRepository carRepository, GalleristCarMapper galleristCarMapper) {
        this.galleristCarRepository = galleristCarRepository;
        this.galleristRepository = galleristRepository;
        this.carRepository = carRepository;
        this.galleristCarMapper = galleristCarMapper;
    }

    @Override
    public DtoGalleristCar createGalleristCar(DtoGalleristCarIU dtoGalleristCarIU) {

        Gallerist gallerist = galleristRepository.findById(dtoGalleristCarIU.getGalleristId())
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, dtoGalleristCarIU.getGalleristId().toString())));

        Car car = carRepository.findById(dtoGalleristCarIU.getCarId())
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, dtoGalleristCarIU.getCarId().toString())));

        GalleristCar galleristCar = galleristCarMapper.dtoGalleristCarIUtoEntity(dtoGalleristCarIU);
        galleristCar.setGallerist(gallerist);
        galleristCar.setCar(car);

        return galleristCarMapper.entityToGalleristCar(galleristCarRepository.save(galleristCar));
    }

    @Override
    public List<DtoGalleristCar> allGalleristCar() {
        return galleristCarRepository.findAll().stream()
                .map(galleristCarMapper::entityToGalleristCar)
                .collect(Collectors.toList());
    }

    @Override
    public DtoGalleristCar getByGalleristCar(Long id) {
        GalleristCar galleristCar = galleristCarRepository.findById(id)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, id.toString())));
        return galleristCarMapper.entityToGalleristCar(galleristCar);
    }

    @Override
    public DtoGalleristCar updateGalleristCar(DtoGalleristCarIU dtoGalleristCarIU, Long id) {

        GalleristCar galleristCar = galleristCarRepository.findById(id)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, id.toString())));

        Gallerist gallerist = galleristRepository.findById(dtoGalleristCarIU.getGalleristId())
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, dtoGalleristCarIU.getGalleristId().toString())));

        Car car = carRepository.findById(dtoGalleristCarIU.getCarId())
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, dtoGalleristCarIU.getCarId().toString())));

        galleristCarMapper.updateGalleristCar(dtoGalleristCarIU, galleristCar);
        galleristCar.setGallerist(gallerist);
        galleristCar.setCar(car);

        return galleristCarMapper.entityToGalleristCar(galleristCarRepository.save(galleristCar));
    }

    @Override
    public void deleteGalleristCar(Long id) {
        GalleristCar galleristCar = galleristCarRepository.findById(id)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, id.toString())));

        galleristCarRepository.delete(galleristCar);

    }
}
