package com.michael.ecommerce.customer;

import jakarta.validation.Valid;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService service;

    @PostMapping
    public ResponseEntity<String> createCustomer(
            @RequestBody @Valid CustomerRequest request) {
        return  ResponseEntity.ok(service.createCustomer(request));
    }

    @PutMapping
    public ResponseEntity<Void> updateCustomer(
            @RequestBody @Valid CustomerRequest request){
        service.updateCustomer(request);
        return ResponseEntity.accepted().build();

    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>>findAllCustomers(){
        return ResponseEntity.ok(service.findAllCustomers());
    }

    @GetMapping("/exists/{customer-id}")
    public ResponseEntity<Boolean> existsByCustomerId(
            @PathVariable("customer-id") String customerId){
                return ResponseEntity.ok(service.existById(customerId));

    }

    @GetMapping("/{customer-id}")
    public ResponseEntity<CustomerResponse> findByCustomerId(
            @PathVariable("customer-id") String customerId){
        return ResponseEntity.ok(service.findByCustomerId(customerId));
    }

    @DeleteMapping("/{customer-Id}")
    public ResponseEntity<Void> deleteCustomer(
            @PathVariable("customer-id") String customerId){
        service.deleteCustomer(customerId);
        return ResponseEntity.accepted().build();
    }





}
