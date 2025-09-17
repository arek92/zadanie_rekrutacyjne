package com.arkadiuszG.demo.service;

import com.arkadiuszG.demo.dTo.CustomerRegistrationDtO;
import com.arkadiuszG.demo.mapper.CustomerDtoMapper;
import com.arkadiuszG.demo.model.Customer;
import com.arkadiuszG.demo.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {


    private final CustomerRepository customerRepository;
    private final CustomerDtoMapper customerDtoMapper;

    public CustomerService(CustomerRepository customerRepository, CustomerDtoMapper customerDtoMapper) {
        this.customerRepository = customerRepository;
        this.customerDtoMapper = customerDtoMapper;
    }

    public Customer registerCustomer(CustomerRegistrationDtO dto) {
        Optional<Customer> existing = customerRepository.findByEmail(dto.email());
        if (existing.isPresent()) {
            throw new IllegalStateException("Customer with this email already exists");
        }
        return customerRepository.save(customerDtoMapper.toEntity(dto));
    }

}
