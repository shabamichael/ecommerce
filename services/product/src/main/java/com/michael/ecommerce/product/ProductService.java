package com.michael.ecommerce.product;

import com.michael.ecommerce.exception.ProductPurchaseException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    public Integer createProduct(@Valid ProductRequest request) {
        var product = mapper.toProduct(request);
        return repository.save(product).getId();
    }

    public ProductResponse findProductById(Integer productId) {
        return repository.findById(productId).map(mapper::toProductResponse)
                .orElseThrow(()-> new EntityNotFoundException("Product not found with the ID:: " + productId));
    }



    public List<ProductResponse> findAllProducts() {
        return repository.findAll()
                .stream()
                .map(mapper::toProductResponse)
                .collect(Collectors.toList());

    }

    public List<ProductPurchaseResponse> purchaseProducts(List<ProductPurchaseRequest> request) {
        var productIds = request.stream()
                .map(ProductPurchaseRequest::productId)
                .toList();
        var storedProducts = repository.findAllByIdInOrderById(productIds);
        if(productIds.size() != storedProducts.size()) {
            throw new ProductPurchaseException("Some products does not exist");
        }
        var storedRequests = request.stream()
                .sorted(Comparator.comparing(ProductPurchaseRequest::productId)).toList();
        var purchasedProducts =new ArrayList<ProductPurchaseResponse>();
        for(int i = 0; i < storedProducts.size(); i++) {
            var product = storedProducts.get(i);
            var requestProduct = storedRequests.get(i);
            if(product.getAvailableQuantity() < requestProduct.quantity()) {
                throw new ProductPurchaseException("Not enough quantity for product with ID:: " + product.getId());
            }
            var newAvailableQuantity = product.getAvailableQuantity() - requestProduct.quantity();
            product.setAvailableQuantity(newAvailableQuantity);
            repository.save(product);
            purchasedProducts.add(mapper.toProductPurchaseResponse(product, requestProduct.quantity()));

        }
        return purchasedProducts;
    }
}
