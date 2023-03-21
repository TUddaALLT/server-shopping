package com.example.shopping.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.shopping.entity.Product;
import com.example.shopping.model.ResponseObject;
import com.example.shopping.model.dto.ProductDTOCreate;
import com.example.shopping.repository.ProductRepository;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Before
    public void setUp() {
        // any setup if necessary
    }

    @Test
    public void testCreateProduct() {
        // given
        ProductDTOCreate productDTOCreate = new ProductDTOCreate();
        productDTOCreate.setImg("image.png");
        productDTOCreate.setName("Test Product");
        productDTOCreate.setBrand("Test Brand");
        productDTOCreate.setOriginal("Test original");
        productDTOCreate.setQuantity(10);
        productDTOCreate.setPrice(19.99);
        productDTOCreate.setAuthor("Test Author");
        productDTOCreate.setCategory("Test Category");
        productDTOCreate.setSuplier("Test Supplier");

        HttpServletRequest request = mock(HttpServletRequest.class);

        Product product = Product.builder()
                .img("image.png")
                .name("Test Product")
                .brand("Test Brand")
                .original("Test original")
                .quantity(10)
                .price(BigDecimal.valueOf(19.99))
                .author("Test Author")
                .category("Test Category")
                .suplier("Test Supplier")
                .build();

        when(productRepository.save(product)).thenReturn(product);

        // when
        // ResponseEntity<ResponseObject> response =
        // productService.createProduct(request, productDTOCreate);

        // then
        // verify(productRepository, times(1)).save(product);
        // assertEquals(HttpStatus.OK, response.getStatusCode());
        // assertEquals("500", response.getBody().getStatus());
        // assertEquals("Create product successfully", response.getBody().getMessage());
        // assertEquals(product, response.getBody().getData());
    }
}