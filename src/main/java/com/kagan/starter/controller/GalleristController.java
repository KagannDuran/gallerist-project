package com.kagan.starter.controller;

import com.kagan.starter.dto.gallerist.DtoGallerist;
import com.kagan.starter.dto.gallerist.DtoGalleristIU;
import com.kagan.starter.service.abstracts.IGalleristService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("rest/api/gallerist")
public class GalleristController {

    private  final IGalleristService galleristService;

    public GalleristController(IGalleristService galleristService) {
        this.galleristService = galleristService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<DtoGallerist>> getAllGallerist()
    {
        return new ResponseEntity<>(galleristService.getAllGallerist(), HttpStatus.OK);
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<DtoGallerist> getGalleristById(@PathVariable Long id)
    {
        return new ResponseEntity<>(galleristService.getGalleristById(id),HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<DtoGallerist> createGallerist(@Valid @RequestBody DtoGalleristIU dtoGalleristIU)
    {
        return new ResponseEntity<>(galleristService.saveGallerist(dtoGalleristIU),HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<DtoGallerist> updateGallerist(@Valid @PathVariable Long id,@RequestBody DtoGalleristIU dtoGalleristIU)
    {
        return new ResponseEntity<>(galleristService.updateGallerist(dtoGalleristIU, id),HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteGallerist(@PathVariable Long id)
    {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
