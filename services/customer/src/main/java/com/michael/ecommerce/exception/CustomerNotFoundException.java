package com.michael.ecommerce.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;


    @Data
    @EqualsAndHashCode(callSuper = true)
    public  class CustomerNotFoundException extends RuntimeException {

        private final String message;
    }

