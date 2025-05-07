package com.kagan.starter.controller;

import com.kagan.starter.dto.galleristcar.DtoGalleristCar;
import com.kagan.starter.dto.galleristcar.DtoGalleristCarIU;
import com.kagan.starter.service.abstracts.IGalleristCarService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("rest/api/galleristcar")
public class GalleristCarController {

    private final IGalleristCarService galleristCarService;

    public GalleristCarController(IGalleristCarService galleristCarService) {
        this.galleristCarService = galleristCarService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<DtoGalleristCar>> allGalleristCar()
    {
        return new ResponseEntity<>(galleristCarService.allGalleristCar(), HttpStatus.OK);
    }

    @GetMapping("list/{id}")
    public ResponseEntity<DtoGalleristCar> getGalleristCarById(@PathVariable Long id)
    {
        return new ResponseEntity<>(galleristCarService.getByGalleristCar(id),HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<DtoGalleristCar> createGalleristCar(@Valid @RequestBody DtoGalleristCarIU dtoGalleristCarIU)
    {
        return new ResponseEntity<>(galleristCarService.createGalleristCar(dtoGalleristCarIU),HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<DtoGalleristCar> updateGalleristCar(@Valid @RequestBody DtoGalleristCarIU dtoGalleristCarIU,@PathVariable Long id)
    {
        return new ResponseEntity<>(galleristCarService.updateGalleristCar(dtoGalleristCarIU, id),HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteGalleristCar(@PathVariable Long id)
    {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
