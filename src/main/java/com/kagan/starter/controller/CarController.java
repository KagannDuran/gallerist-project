package com.kagan.starter.controller;

import com.kagan.starter.dto.car.DtoCar;
import com.kagan.starter.dto.car.DtoCarIU;
import com.kagan.starter.service.abstracts.ICarService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("rest/api/car")
public class CarController {

    private final ICarService carService;

    public CarController(ICarService carService) {
        this.carService = carService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<DtoCar>> getAllCars()
    {
        return new ResponseEntity<>(carService.getAllCar(), HttpStatus.OK);
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<DtoCar> getCarById(@PathVariable Long id)
    {
        return new ResponseEntity<>(carService.getByCarId(id), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<DtoCar> createCar(@Valid  @RequestBody DtoCarIU dtoCarIU)
    {
        return new ResponseEntity<>(carService.createCar(dtoCarIU), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<DtoCar> updateCar(@Valid @RequestBody DtoCarIU dtoCarIU,@PathVariable Long id)
    {
        return new ResponseEntity<>(carService.updateCar(dtoCarIU, id), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id)
    {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
