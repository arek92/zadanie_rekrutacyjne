package com.arkadiuszG.demo.dTo;

public record CustomerDTO(
        Long id,
        String firstName,
        String lastName,
        String email,
        String address
) {
}
