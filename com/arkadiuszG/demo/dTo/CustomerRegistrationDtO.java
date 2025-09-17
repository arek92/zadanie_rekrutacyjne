package com.arkadiuszG.demo.dTo;

public record CustomerRegistrationDtO(
        String firstName,
        String lastName,
        String email,
        String address
) {

    @Override
    public String firstName() {
        return firstName;
    }

    @Override
    public String lastName() {
        return lastName;
    }

    @Override
    public String email() {
        return email;
    }

    @Override
    public String address() {
        return address;
    }
}
