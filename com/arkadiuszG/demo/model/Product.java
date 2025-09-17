package com.arkadiuszG.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private BigDecimal priceNetto;
    private BigDecimal vatRate;


    public Product(Long id, String name, BigDecimal priceNetto, BigDecimal vatRate) {
        this.id = id;
        this.name = name;
        this.priceNetto = priceNetto;
        this.vatRate = vatRate;
    }

    public Product() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPriceNetto() {
        return priceNetto;
    }

    public void setPriceNetto(BigDecimal priceNetto) {
        this.priceNetto = priceNetto;
    }

    public BigDecimal getVatRate() {
        return vatRate;
    }

    public void setVatRate(BigDecimal vatRate) {
        this.vatRate = vatRate;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) && Objects.equals(name, product.name) && Objects.equals(priceNetto, product.priceNetto) && Objects.equals(vatRate, product.vatRate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, priceNetto, vatRate);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", priceNetto=" + priceNetto +
                ", vatRate=" + vatRate +
                '}';
    }
}
