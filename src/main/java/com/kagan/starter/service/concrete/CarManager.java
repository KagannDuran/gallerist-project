package com.kagan.starter.service.concrete;

import com.kagan.starter.dto.car.DtoCar;
import com.kagan.starter.dto.car.DtoCarIU;
import com.kagan.starter.entity.Car;
import com.kagan.starter.exception.BaseException;
import com.kagan.starter.exception.ErrorMessage;
import com.kagan.starter.exception.MessageType;
import com.kagan.starter.mapper.CarMapper;
import com.kagan.starter.repository.CarRepository;
import com.kagan.starter.service.abstracts.ICarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarManager implements ICarService {

   private final CarRepository carRepository;
   private final CarMapper carMapper;

    public CarManager(CarRepository carRepository, CarMapper carMapper) {
        this.carRepository = carRepository;
        this.carMapper = carMapper;
    }

    @Override
    public DtoCar createCar(DtoCarIU dtoCarIU) {
        Car car = carMapper.dtoCarIUtoEntity(dtoCarIU);
        return carMapper.entityToDtoCar(carRepository.save(car));
    }


    @Override
    public List<DtoCar> getAllCar() {

       List<Car> cars = carRepository.findAll();

       return cars.stream().map(carMapper::entityToDtoCar).collect(Collectors.toList());
    }

    @Override
    public DtoCar getByCarId(Long id) {

       Car car = carRepository.findById(id)
               .orElseThrow(()->new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST,id.toString())));

       return carMapper.entityToDtoCar(car);
    }

    @Override
    public DtoCar updateCar(DtoCarIU dtoCarIU, Long id) {

       Car car = carRepository.findById(id)
               .orElseThrow(()->new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST,id.toString())));

       carMapper.updateDtoCarIUtoCar(dtoCarIU,car);

       return carMapper.entityToDtoCar(carRepository.save(car));
    }

    @Override
    public void deleteCar(Long id) {
       Car car = carRepository.findById(id)
               .orElseThrow(()->new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST,id.toString())));

       carRepository.delete(car);
    }
}
