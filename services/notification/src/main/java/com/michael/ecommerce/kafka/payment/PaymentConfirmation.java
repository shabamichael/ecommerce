package com.michael.ecommerce.kafka.payment;

import java.math.BigDecimal;

public record PaymentConfirmation(
        String orderReference,
       BigDecimal amount,
        String paymentMethod,
        String customerFirstName,
        String customerLastName,
        String customerEmail

) {
}
