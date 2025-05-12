package com.michael.ecommerce.product;

import com.michael.ecommerce.category.Category;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


class ProductServiceTest {
    //    Service under test
    @InjectMocks
    private ProductService productService;

    //    Dependencies
    @Mock
    private  ProductRepository repository;
    @Mock
    private  ProductMapper mapper;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void shouldSuccessfullyCreateProduct() {
        // Given
        ProductRequest request = new ProductRequest(
                1,
                "Product Name",
                "Product Description",
                10.0,
                BigDecimal.valueOf(100.0),
                1
        );
        //Product object from the request mapping
        ArrayList<Product> products = new ArrayList<>();
        Category category = new Category(
                1,
                "Category Name",
                "Category Description",
                products);
        Product product = new Product(
                1,
                "Product Name",
                "Product Description",
                10.0,
                BigDecimal.valueOf(100.0),
                category
        );
        //The Product object that will be saved in the database
        Product productToSave = new Product(
                1,
                "Product Name",
                "Product Description",
                10.0,
                BigDecimal.valueOf(100.0),
                category
        );
        //Mock the calls to the repository and mapper
        when(mapper.toProduct(request)).thenReturn(product);
        when(repository.save(product)).thenReturn(product);

        // When
        Integer id = productService.createProduct(request);

        // Then
        assertEquals(request.id(), id);


        verify(repository,times(1)).save(product);
        verify(mapper,times(1)).toProduct(request);
    }

    @Test
    void shouldReturnProductById() {
        // Given
        Integer productId = 1;
        Product product = new Product(
                1,
                "Product Name",
                "Product Description",
                10.0,
                BigDecimal.valueOf(100.0),
                new Category(
                        1,
                        "Category Name",
                        "Category Description",
                        null
                )
        );
        //Mock the call repository to return the product
        when(repository.findById(productId)).thenReturn(Optional.of(product));

        //Mock the mapper to return the product response
        when(mapper.toProductResponse(any(Product.class)))
                .thenReturn(new ProductResponse(
                        1,
                        "Product Name",
                        "Product Description",
                        10.0,
                        BigDecimal.valueOf(100.0),
                        1,
                        "Category Name",
                        "Category Description"
                ));

        // When
        ProductResponse productResponse = productService.findProductById(productId);

        // Then
        assertEquals(product.getId(), productResponse.id());
        assertEquals(product.getName(), productResponse.name());
        assertEquals(product.getDescription(), productResponse.description());
        assertEquals(product.getAvailableQuantity(), productResponse.availableQuantity());
        assertEquals(product.getPrice(), productResponse.price());
        assertEquals(product.getCategory().getId(), productResponse.categoryId());
        assertEquals(product.getCategory().getName(), productResponse.categoryName());
        assertEquals(product.getCategory().getDescription(), productResponse.categoryDescription());
        //Verify that the repository and mapper methods were called once
        verify(repository, times(1)).findById(productId);
    }

    @Test
    void shouldReturnAllProducts() {
        // Given
        List<Product> products = new ArrayList<>();
        products.add(new Product(
                1,
                "Product Name",
                "Product Description",
                10.0,
                BigDecimal.valueOf(100.0),
                new Category(
                        1,
                        "Category Name",
                        "Category Description",
                        products
                )
        ));
        //Mock the repository to return the list of products
        when(repository.findAll()).thenReturn(products);
        //Mock the mapper to return the list of product responses
        when(mapper.toProductResponse(any(Product.class)))
                .thenReturn(new ProductResponse(
                1,
                "Product Name",
                "Product Description",
                10.0,
                BigDecimal.valueOf(100.0),
                        1,
                        "Category Name",
                        "Category Description"
        ));
        //when
        List<ProductResponse> productResponses = productService.findAllProducts();

        //Then
        assertEquals(products.size(), productResponses.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void shouldPurchaseProducts() {
        // Given
        //When
        //Then

    }
}