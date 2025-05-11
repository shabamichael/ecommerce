package com.michael.ecommerce.product;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private  final ProductService service;


    @PostMapping
    public ResponseEntity<Integer> createProduct(
            @RequestBody @Valid ProductRequest request) {
        return  ResponseEntity.ok(service.createProduct(request));
    }

    @PostMapping("/purchase")
    public ResponseEntity<List<ProductPurchaseResponse>> purchaseRequest(
            @RequestBody List<ProductPurchaseRequest> request) {
        return ResponseEntity.ok(service.purchaseProducts(request));
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductResponse> findById(
            @PathVariable("id") Integer productId) {
        return  ResponseEntity.ok(service.findProductById(productId));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAll() {
        return ResponseEntity.ok(service.findAllProducts());
    }
}
