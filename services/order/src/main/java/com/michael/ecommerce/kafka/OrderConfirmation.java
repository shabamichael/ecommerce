package com.michael.ecommerce.kafka;

import com.michael.ecommerce.customer.CustomerResponse;
import com.michael.ecommerce.order.PaymentMethod;
import com.michael.ecommerce.product.PurchaseResponse;
import java.util.List;

import java.math.BigDecimal;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customerResponse,
        List<PurchaseResponse> products
) {
}
