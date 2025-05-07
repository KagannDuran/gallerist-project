package com.kagan.starter.controller;

import com.kagan.starter.dto.saledCar.DtoSaledCar;
import com.kagan.starter.dto.saledCar.DtoSaledCarIU;
import com.kagan.starter.service.abstracts.ISaledCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("rest/api/saledcar")
public class SaledCarController {

    private final ISaledCarService saledCarService;

    public SaledCarController(ISaledCarService saledCarService) {
        this.saledCarService = saledCarService;
    }

    @GetMapping("list")
    public ResponseEntity<List<DtoSaledCar>> allSaledCar()
    {
        return new ResponseEntity<>(saledCarService.allSaledCar(), HttpStatus.OK);
    }

    @GetMapping("list/{id}")
    public ResponseEntity<DtoSaledCar> saledCarById(@PathVariable Long id)
    {
        return new ResponseEntity<>(saledCarService.saledCarById(id),HttpStatus.OK);
    }

    @PostMapping("create")
    public ResponseEntity<DtoSaledCar> createSaledCar(@RequestBody DtoSaledCarIU dtoSaledCarIU)
    {
        return new ResponseEntity<>(saledCarService.buyCar(dtoSaledCarIU),HttpStatus.CREATED);
    }

}
