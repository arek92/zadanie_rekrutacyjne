package com.arkadiuszG.demo.mapper;

import com.arkadiuszG.demo.dTo.CustomerRegistrationDtO;
import com.arkadiuszG.demo.model.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerDtoMapper {

    public Customer toEntity(CustomerRegistrationDtO dto) {
        return new Customer(
                dto.firstName(),
                dto.lastName(),
                dto.email(),
                dto.address()
        );
    }
}
