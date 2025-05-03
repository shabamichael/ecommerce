package com.michael.ecommerce.product;

import com.michael.ecommerce.category.Category;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ProductMapper {
    public Product toProduct(@Valid ProductRequest request) {
        return Product.builder()
                .id(request.id())
                .availableQuantity(request.availableQuantity())
                .name(request.name())
                .description(request.description())
                .price(request.price())
                .category(Category.builder()
                                .id(request.categoryId())
                                .name(request.name())
                                .description(request.description())
                                .build())
                .build();
    }


    public ProductResponse toProductResponse( Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getAvailableQuantity(),
                product.getPrice(),
                product.getCategory().getId(),
                product.getCategory().getName(),
                product.getCategory().getDescription()
        );
    }

    public ProductPurchaseResponse toProductPurchaseResponse(Product product, double quantity) {
        return new ProductPurchaseResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                quantity

                );
    }
}
