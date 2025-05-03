package com.michael.ecommerce.customer;

public record CustomerResponse(
        String id,
        String firstName,
        String lastName,
        String email

) {
}
