package com.michael.ecommerce.product;

import java.math.BigDecimal;

public record ProductPurchaseResponse(
        Integer productId,
        String productName,
        String productDescription,

        BigDecimal totalPrice,
        double quantity

){
}
