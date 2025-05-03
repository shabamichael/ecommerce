package com.michael.ecommerce.product;

public record PurchaseResponse(
        Integer productId,
        String name,
        String description,
        double quantity
){
}
