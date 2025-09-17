package com.arkadiuszG.demo.dTo;

import java.math.BigDecimal;
import java.util.List;

public record OrderResponseDTO(
        Long orderId,
        CustomerDTO customer,
        List<OrderItemResponseDTO> items,
        BigDecimal totalNetto,
        BigDecimal totalBrutto
) {
}
