package com.example.shopping.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.shopping.model.ResponseObject;
import com.example.shopping.model.dto.ProductDTOCreate;
import com.example.shopping.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<ResponseObject> products() {
        System.out.println("get products");
        return productService.getProducts();
    }

    @PostMapping("/create/product")
    public ResponseEntity<ResponseObject> addProduct(HttpServletRequest request,
            @RequestBody ProductDTOCreate productDTOCreate) {
        return productService.createProduct(request, productDTOCreate);
    }

    @GetMapping("/buy/{id}")
    public ResponseEntity<ResponseObject> deleteTask(HttpServletRequest request,
            @PathVariable int id) {
        System.out.println("delete");
        return productService.findById(request, id);
    }
}
