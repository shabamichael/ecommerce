package com.michael.ecommerce.notification;


import com.michael.ecommerce.kafka.payment.PaymentMethod;

import java.math.BigDecimal;

public record PaymentNotificationRequest(
            String orderReference,
            BigDecimal amount,
            PaymentMethod paymentMethod,
            String customerFirstname,
            String customerLastname,
            String customerEmail
    ) {
    }

