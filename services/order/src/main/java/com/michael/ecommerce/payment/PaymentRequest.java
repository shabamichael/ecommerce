package com.michael.ecommerce.payment;

import com.michael.ecommerce.customer.CustomerResponse;
import com.michael.ecommerce.order.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(

        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
) {
}
