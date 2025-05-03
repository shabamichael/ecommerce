package com.michael.ecommerce.email;

import lombok.Getter;

public enum EmailTemplates{
    ORDER_CONFIRMATION("order-confirmation.html", "Order confirmation"),
    PAYMENT_CONFIRMATION("payment-confirmation.html" , "Payment successfully processed");

    @Getter
    private final String templateName;
    @Getter
    private final String subject;

    EmailTemplates(String templateName, String subject) {
        this.templateName = templateName;
        this.subject = subject;
    }

    }
