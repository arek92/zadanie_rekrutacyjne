package com.arkadiuszG.demo.dTo;

import java.util.List;

public record OrderRequestDTO(
        Long customerId,
        List<OrderItemDTO> items
) {
}
