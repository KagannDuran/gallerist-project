package com.kagan.starter.service.concrete;

import com.kagan.starter.dto.saledCar.DtoSaledCar;
import com.kagan.starter.dto.saledCar.DtoSaledCarIU;
import com.kagan.starter.entity.Car;
import com.kagan.starter.entity.Customer;
import com.kagan.starter.entity.Gallerist;
import com.kagan.starter.entity.SaledCar;
import com.kagan.starter.enums.CarStatusType;
import com.kagan.starter.exception.BaseException;
import com.kagan.starter.exception.ErrorMessage;
import com.kagan.starter.exception.MessageType;
import com.kagan.starter.mapper.SaledCarMapper;
import com.kagan.starter.repository.CarRepository;
import com.kagan.starter.repository.CustomerRepository;
import com.kagan.starter.repository.GalleristRepository;
import com.kagan.starter.repository.SaledCarRepository;
import com.kagan.starter.service.abstracts.ISaledCarService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class SaledCarManager implements ISaledCarService {
    private final SaledCarRepository saledCarRepository;
    private final GalleristRepository galleristRepository;
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;
    private final SaledCarMapper saledCarMapper;

    public SaledCarManager(SaledCarRepository saledCarRepository, GalleristRepository galleristRepository, CarRepository carRepository, CustomerRepository customerRepository, SaledCarMapper saledCarMapper) {
        this.saledCarRepository = saledCarRepository;
        this.galleristRepository = galleristRepository;
        this.carRepository = carRepository;
        this.customerRepository = customerRepository;
        this.saledCarMapper = saledCarMapper;
    }


    private void checkAmount(Customer customer, Car car)
    {
        if(customer.getAccount().getPrice().compareTo(car.getPrice())<0)
        {
            throw new BaseException(new ErrorMessage(MessageType.CUSTOMER_PRICE_IS_NOT_ENOUGH, null));
        }


    }

    private void checkCarStatus(Car car)
    {
        if(car.getCarStatusType() == CarStatusType.SALED)
        {
            throw new BaseException(new ErrorMessage(MessageType.CAR_STATUS_IS_ALREADY_SALED, null));
        }
    }




    @Transactional
    @Override
    public DtoSaledCar buyCar(DtoSaledCarIU dtoSaledCarIU) {

        // Gerekli varlıklar çekilir
        Customer customer = customerRepository.findById(dtoSaledCarIU.getCustomerId())
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.NO_RECORD_EXIST, dtoSaledCarIU.getCustomerId().toString())));

        Car car = carRepository.findById(dtoSaledCarIU.getCarId())
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.NO_RECORD_EXIST, dtoSaledCarIU.getCarId().toString())));

        Gallerist gallerist = galleristRepository.findById(dtoSaledCarIU.getGalleristId())
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.NO_RECORD_EXIST, dtoSaledCarIU.getGalleristId().toString())));

        // İş kuralları kontrol edilir
        checkCarStatus(car);
        checkAmount(customer, car);

        // Satış işlemi gerçekleştirilir
        SaledCar saledCar = saledCarMapper.iuToEntity(dtoSaledCarIU);
        saledCar.setCar(car);
        saledCar.setCustomer(customer);
        saledCar.setGallerist(gallerist);

        SaledCar savedSaledCar = saledCarRepository.save(saledCar);

        // Arabanın durumu güncellenir
        car.setCarStatusType(CarStatusType.SALED);
        carRepository.save(car);

        // Müşteri bakiyesi güncellenir
        BigDecimal newBalance = customer.getAccount().getPrice().subtract(car.getPrice());
        customer.getAccount().setPrice(newBalance);
        customerRepository.save(customer); //

        return saledCarMapper.entitytoDto(savedSaledCar);
    }

    @Override
    public List<DtoSaledCar> allSaledCar() {
        return saledCarRepository.findAll().stream().map(saledCarMapper::entitytoDto).toList();
    }

    @Override
    public DtoSaledCar saledCarById(Long id) {

        SaledCar saledCar = saledCarRepository.findById(id)
                .orElseThrow(()->new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST,id.toString())));

        return saledCarMapper.entitytoDto(saledCar);
    }

}
