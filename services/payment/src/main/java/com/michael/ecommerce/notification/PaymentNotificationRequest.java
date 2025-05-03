package com.michael.ecommerce.notification;

import com.michael.ecommerce.payment.PaymentMethod;

public record PaymentNotificationRequest(
    String orderReference,
    String amount,
    PaymentMethod paymentMethod,
    String customerEmail,
    String customerFirstName,
    String customerLastName){
}
