package com.example.shopping.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;

import com.example.shopping.model.ResponseObject;
import com.example.shopping.model.dto.ProductDTOCreate;

public interface ProductService {

    ResponseEntity<ResponseObject> getProducts();

    ResponseEntity<ResponseObject> createProduct(HttpServletRequest request, ProductDTOCreate productDTOCreate);

    ResponseEntity<ResponseObject> findById(HttpServletRequest request, int id);

}
