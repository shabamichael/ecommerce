package com.michael.ecommerce.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record CustomerRequest(

            String id,

            @NotNull(message = "Customers firstname is required")
            String firstName,

            @NotNull(message = "Customers lastname is required")
            String lastName,

            @NotNull(message = "Customers email is required")
            @Email(message = "Customers email is not a valid email address")
            String email,

            Address address
    ) {
    }
