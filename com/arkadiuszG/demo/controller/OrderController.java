package com.arkadiuszG.demo.controller;

import com.arkadiuszG.demo.dTo.*;
import com.arkadiuszG.demo.model.Customer;
import com.arkadiuszG.demo.model.Order;
import com.arkadiuszG.demo.model.OrderItem;
import com.arkadiuszG.demo.model.Product;
import com.arkadiuszG.demo.repository.CustomerRepository;
import com.arkadiuszG.demo.repository.OrderRepository;
import com.arkadiuszG.demo.repository.ProductRepository;
import com.arkadiuszG.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
public class OrderController {


    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/createOrder")
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderRequestDTO request) {
        OrderResponseDTO responseDTO = orderService.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping("/findOrderById/{id}")
    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }
}