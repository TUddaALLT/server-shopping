package com.example.shopping.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.shopping.entity.Product;
import com.example.shopping.model.ResponseObject;
import com.example.shopping.model.dto.ProductDTOCreate;
import com.example.shopping.repository.ProductRepository;
import com.example.shopping.service.ProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
        private final ProductRepository productRepository;

        @Override
        public ResponseEntity<ResponseObject> getProducts() {

                List<Product> products = new ArrayList<>();
                try {
                        products = productRepository.findAll();
                } catch (Exception e) {
                        products = new ArrayList<>();
                }
                if (products.size() != 0) {
                        return ResponseEntity.ok()
                                        .body(ResponseObject.builder().status("500").message("success").data(products)
                                                        .build());
                }
                return ResponseEntity.ok()
                                .body(ResponseObject.builder().status("400").message("out stock").data(null)
                                                .build());
        }

        @Override
        public ResponseEntity<ResponseObject> createProduct(HttpServletRequest request,
                        ProductDTOCreate productDTOCreate) {
                Product product = Product.builder()
                                .img(productDTOCreate.getImg())
                                .name(productDTOCreate.getName())
                                .brand(productDTOCreate.getBrand())
                                .original(productDTOCreate.getOriginal())
                                .quantity(productDTOCreate.getQuantity())
                                .price(BigDecimal.valueOf(productDTOCreate.getPrice()))
                                .author(productDTOCreate.getAuthor())
                                .category(productDTOCreate.getCategory())
                                .suplier(productDTOCreate.getSuplier())
                                .build();
                productRepository.save(product);
                return ResponseEntity.ok()
                                .body(ResponseObject.builder().status("500").message("Create product successfully")
                                                .data(product)
                                                .build());
        }

        @Override
        public ResponseEntity<ResponseObject> findById(HttpServletRequest request, int id) {
                Optional<Product> DBproduct = productRepository.findById(id);
                Product product = null;
                if (DBproduct.isPresent()) {
                        product = DBproduct.get();
                } else {
                        return ResponseEntity.ok()
                                        .body(ResponseObject.builder().status("400").message("Product is not exist")
                                                        .data(null)
                                                        .build());
                }
                return ResponseEntity.ok()
                                .body(ResponseObject.builder().status("500").message("get product by id successfully")
                                                .data(product)
                                                .build());
        }

}
