package com.arkadiuszG.demo.model;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    private BigDecimal totalNetto;
    private BigDecimal totalBrutto;

    public Order(Long id, Customer customer, List<OrderItem> items, BigDecimal totalNetto, BigDecimal totalBrutto) {
        this.id = id;
        this.customer = customer;
        this.items = items;
        this.totalNetto = totalNetto;
        this.totalBrutto = totalBrutto;
    }

    public Order() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public BigDecimal getTotalNetto() {
        return totalNetto;
    }

    public void setTotalNetto(BigDecimal totalNetto) {
        this.totalNetto = totalNetto;
    }

    public BigDecimal getTotalBrutto() {
        return totalBrutto;
    }

    public void setTotalBrutto(BigDecimal totalBrutto) {
        this.totalBrutto = totalBrutto;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) && Objects.equals(customer, order.customer) && Objects.equals(items, order.items) && Objects.equals(totalNetto, order.totalNetto) && Objects.equals(totalBrutto, order.totalBrutto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customer, items, totalNetto, totalBrutto);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", customer=" + customer +
                ", items=" + items +
                ", totalNetto=" + totalNetto +
                ", totalBrutto=" + totalBrutto +
                '}';
    }

    public void calculateTotals() {
        totalNetto = items.stream()
                .map(item -> item.getProduct().getPriceNetto().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        totalBrutto = items.stream()
                .map(item -> item.getProduct().getPriceNetto()
                        .multiply(BigDecimal.valueOf(item.getQuantity()))
                        .multiply(BigDecimal.ONE.add(item.getProduct().getVatRate())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}


