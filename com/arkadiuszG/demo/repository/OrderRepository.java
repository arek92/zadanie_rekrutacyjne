package com.arkadiuszG.demo.repository;

import com.arkadiuszG.demo.dTo.OrderResponseDTO;
import com.arkadiuszG.demo.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    Optional<Order> findById(Long id);
}
