package com.example.shopping.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;

import com.example.shopping.model.ResponseObject;

public interface CartService {

    ResponseEntity<ResponseObject> addToCart(HttpServletRequest request, int id, int quantity);

    ResponseEntity<ResponseObject> getMyCart(HttpServletRequest request);

    ResponseEntity<ResponseObject> updateCartDetails(HttpServletRequest request, int id, int quantity);

    ResponseEntity<ResponseObject> removeProducts(HttpServletRequest request);

    ResponseEntity<ResponseObject> removeCart(HttpServletRequest request, int id);
}
