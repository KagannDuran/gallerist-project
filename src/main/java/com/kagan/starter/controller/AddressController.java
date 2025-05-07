package com.kagan.starter.controller;

import com.kagan.starter.dto.address.DtoAddress;
import com.kagan.starter.dto.address.DtoAddressUI;
import com.kagan.starter.service.abstracts.IAddressService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("rest/api/address")
public class AddressController {

    private final IAddressService addressService;

    public AddressController(IAddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("/create")
    public ResponseEntity<DtoAddress> createAddress(@Valid @RequestBody DtoAddressUI dtoAddressUI)
    {
        return new ResponseEntity<>(addressService.saveAddress(dtoAddressUI), HttpStatus.CREATED);
    }

    @GetMapping("/list/{id}")
    public  ResponseEntity<DtoAddress> getAddressById(@Valid @PathVariable Long id)
    {
        return new ResponseEntity<>(addressService.getAddressById(id),HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<DtoAddress>> getAllAddress()
    {
        return new ResponseEntity<>(addressService.getListAddress(),HttpStatus.OK);
    }
}
