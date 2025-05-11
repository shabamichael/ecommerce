package com.michael.ecommerce.product;

import com.michael.ecommerce.category.Category;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductMapperTest {

    private ProductMapper productMapper;

    @BeforeEach
    void setUp() {
       productMapper = new ProductMapper();
    }

    @Test
    public void shouldMapProductToProductRequest(){
        //given
        ProductRequest productRequest = new ProductRequest(
                1,
                "NoteBook",
                "This is a note book",
                1000,
                BigDecimal.valueOf(1000.00),
                10
        );

        //when
        Product product = productMapper.toProduct(productRequest);

        //then
        assertEquals(productRequest.id(), product.getId());
        assertEquals(productRequest.price(), product.getPrice());
        assertEquals(productRequest.name(), product.getName());
        assertEquals(productRequest.availableQuantity(), product.getAvailableQuantity());
        assertEquals(productRequest.description(), product.getDescription());
    }

    @Test
    public void shouldMapProductToProductResponse(){
        //Given
        ArrayList<Product> products = new ArrayList<>();
        Category category = new Category(
                1,
                "Stationary",
                "This is a stationary category",
                products
        );
        Product product = new Product(
                1,
                "NoteBook",
                "This is a note book",
                1000,
                BigDecimal.valueOf(1000.00),
                category
        );

        //When
        ProductResponse productResponse = productMapper.toProductResponse(product);

        //Then
        assertEquals(product.getId(), productResponse.id());
        assertEquals(product.getPrice(), productResponse.price());
        assertEquals(product.getName(), productResponse.name());
        assertEquals(product.getAvailableQuantity(), productResponse.availableQuantity());
        assertEquals(product.getDescription(), productResponse.description());
        assertEquals(product.getCategory().getId(), productResponse.categoryId());
    }


    @Test
    public void shouldMapProductToProductPurchaseResponse(){
        //Given

        Product product = new Product(
                1,
                "NoteBook",
                "This is a note book",
                1000,
                BigDecimal.valueOf(1000.00),
                null
        );

        //When
        ProductPurchaseResponse productPurchaseResponse = productMapper.toProductPurchaseResponse(product, 50);

        //Then
        assertEquals(productPurchaseResponse.productId(), product.getId());
        assertEquals(productPurchaseResponse.productName(), product.getName());
        assertEquals(productPurchaseResponse.productDescription(), product.getDescription());
        assertEquals(productPurchaseResponse.totalPrice(), product.getPrice());
        assertEquals(productPurchaseResponse.quantity(), 50);
    }


}