package com.arkadiuszG.demo.controller;

import com.arkadiuszG.demo.dTo.CustomerRegistrationDtO;
import com.arkadiuszG.demo.mapper.CustomerDtoMapper;
import com.arkadiuszG.demo.model.Customer;
import com.arkadiuszG.demo.repository.CustomerRepository;
import com.arkadiuszG.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/registerCustomer")
    public ResponseEntity<?> registerCustomer(@RequestBody CustomerRegistrationDtO dto) {
        try {
            Customer saved = customerService.registerCustomer(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (IllegalStateException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        }
    }
}
