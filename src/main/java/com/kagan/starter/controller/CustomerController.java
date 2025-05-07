package com.kagan.starter.controller;

import com.kagan.starter.dto.customer.DtoCustomer;
import com.kagan.starter.dto.customer.DtoCustomerIU;
import com.kagan.starter.service.abstracts.ICustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("rest/api/customer")
public class CustomerController {

    private final ICustomerService customerService;

    public CustomerController(ICustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<DtoCustomer>> getAll()
    {
        List<DtoCustomer> dtoCustomers = customerService.getAllCustomer();
        return new ResponseEntity<>(dtoCustomers, HttpStatus.OK);
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<DtoCustomer> getCustomerById(@PathVariable Long id)
    {
        DtoCustomer dtoCustomer = customerService.getByCustomerId(id);
        return new ResponseEntity<>(dtoCustomer,HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<DtoCustomer> createCustomer(@Valid @RequestBody DtoCustomerIU dtoCustomerIU)
    {
        DtoCustomer dtoCustomer = customerService.saveCustomer(dtoCustomerIU);
        return new ResponseEntity<>(dtoCustomer,HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<DtoCustomer> updateCustomer(@Valid @PathVariable Long id,@RequestBody DtoCustomerIU dtoCustomerIU)
    {
        DtoCustomer dtoCustomer = customerService.updateCustomer(dtoCustomerIU, id);
        return new ResponseEntity<>(dtoCustomer,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id)
    {
        customerService.deleteCustomer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
