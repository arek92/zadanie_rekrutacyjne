package com.arkadiuszG.demo.dTo;

import java.math.BigDecimal;

public record OrderItemResponseDTO(
        String productName,
        int quantity,
        BigDecimal priceNetto,
        BigDecimal priceBrutto
) {
    @Override
    public String productName() {
        return productName;
    }

    @Override
    public BigDecimal priceBrutto() {
        return priceBrutto;
    }

    @Override
    public BigDecimal priceNetto() {
        return priceNetto;
    }

    @Override
    public int quantity() {
        return quantity;
    }
}
