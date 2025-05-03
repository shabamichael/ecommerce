package com.michael.ecommerce.kafka;

import java.math.BigDecimal;

public record Product(
        Integer ProductId,
        String name,
        String description,
        BigDecimal price,
        double quantity
) {
}
