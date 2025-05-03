package com.michael.ecommerce.customer;

import jakarta.validation.Valid;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
        public Customer toCustomer(@Valid CustomerRequest request) {
            if (request == null) return null;

            return Customer.builder()
                    .id(request.id())
                    .firstName(request.firstName())
                    .lastName(request.lastName())
                    .email(request.email())
                    .address(request.address())
                    .build();
        }

        public CustomerResponse fromCustomer(Customer customer) {
            return new CustomerResponse(
                    customer.getId(),
                    customer.getFirstName(),
                    customer.getLastName(),
                    customer.getEmail(),
                    customer.getAddress()
            );
        }
    }
