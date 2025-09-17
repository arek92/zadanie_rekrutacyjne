package com.arkadiuszG.demo.service;

import com.arkadiuszG.demo.dTo.*;
import com.arkadiuszG.demo.model.Customer;
import com.arkadiuszG.demo.model.Order;
import com.arkadiuszG.demo.model.OrderItem;
import com.arkadiuszG.demo.model.Product;
import com.arkadiuszG.demo.repository.CustomerRepository;
import com.arkadiuszG.demo.repository.OrderRepository;
import com.arkadiuszG.demo.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public OrderResponseDTO createOrder(OrderRequestDTO request) {
        Customer customer = customerRepository.findById(request.customerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal totalNetto = BigDecimal.ZERO;
        BigDecimal totalBrutto = BigDecimal.ZERO;

        for (OrderItemDTO itemDTO : request.items()) {
            Product product = productRepository.findById(itemDTO.productId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            BigDecimal netto = product.getPriceNetto().multiply(BigDecimal.valueOf(itemDTO.quantity()));
            BigDecimal brutto = netto.multiply(BigDecimal.ONE.add(product.getVatRate()));

            OrderItem orderItem = new OrderItem(null, null, product, itemDTO.quantity());
            orderItems.add(orderItem);

            totalNetto = totalNetto.add(netto);
            totalBrutto = totalBrutto.add(brutto);
        }

        Order order = new Order(null, customer, orderItems, totalNetto, totalBrutto);
        orderItems.forEach(item -> item.setOrder(order));

        orderRepository.save(order);

        List<OrderItemResponseDTO> itemsResponse = orderItems.stream()
                .map(i -> new OrderItemResponseDTO(
                        i.getProduct().getName(),
                        i.getQuantity(),
                        i.getProduct().getPriceNetto(),
                        i.getProduct().getPriceNetto().multiply(BigDecimal.ONE.add(i.getProduct().getVatRate()))
                ))
                .toList();

        return new OrderResponseDTO(
                order.getId(),
                new CustomerDTO(customer.getId(), customer.getFirstName(), customer.getLastName(),
                        customer.getEmail(), customer.getAddress()),
                itemsResponse,
                totalNetto,
                totalBrutto
        );
    }


    public OrderResponseDTO getOrderById(Long orderId) {
        Order order = orderRepository.findById(Math.toIntExact(orderId))
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id " + orderId));

        CustomerDTO customerDTO = new CustomerDTO(
                order.getCustomer().getId(),
                order.getCustomer().getFirstName(),
                order.getCustomer().getLastName(),
                order.getCustomer().getEmail(),
                order.getCustomer().getAddress()
        );

        List<OrderItemResponseDTO> items = order.getItems().stream()
                .map(item -> {
                    BigDecimal netto = item.getProduct().getPriceNetto();
                    BigDecimal vatRate = item.getProduct().getVatRate(); // np. 0.23
                    BigDecimal brutto = netto.multiply(BigDecimal.ONE.add(vatRate));

                    return new OrderItemResponseDTO(
                            item.getProduct().getName(),
                            item.getQuantity(),
                            netto,
                            brutto
                    );
                })
                .toList();

        BigDecimal totalNetto = items.stream()
                .map(i -> i.priceNetto().multiply(BigDecimal.valueOf(i.quantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalBrutto = items.stream()
                .map(i -> i.priceBrutto().multiply(BigDecimal.valueOf(i.quantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new OrderResponseDTO(order.getId(), customerDTO, items, totalNetto, totalBrutto);
    }
}
