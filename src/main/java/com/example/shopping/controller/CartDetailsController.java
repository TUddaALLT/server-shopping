package com.example.shopping.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.shopping.model.ResponseObject;
import com.example.shopping.service.CartService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class CartDetailsController {
    private final CartService cartService;

    @PostMapping("/cartdetails/{id}/{quantity}")
    public ResponseEntity<ResponseObject> updateCartDetails(HttpServletRequest request,
            @PathVariable int id, @PathVariable int quantity) {
        System.out.println("update quantity");
        return cartService.updateCartDetails(request, id, quantity);
    }

}
