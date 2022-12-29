package com.example.shopping.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.shopping.model.ResponseObject;
import com.example.shopping.service.CartService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class CartController {
    private final CartService cartService;

    @PostMapping("/cart/{id}/{quantity}")
    public ResponseEntity<ResponseObject> cart(HttpServletRequest request,
            @PathVariable int id, @PathVariable int quantity) {
        System.out.println("con me bug");
        return cartService.addToCart(request, id, quantity);
    }

    @GetMapping("/cart/mycart")
    public ResponseEntity<ResponseObject> cart(HttpServletRequest request) {
        return cartService.getMyCart(request);
    }
}
