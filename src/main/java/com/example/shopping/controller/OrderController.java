package com.example.shopping.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.shopping.model.ResponseObject;
import com.example.shopping.model.dto.OrderCreateDTO;
import com.example.shopping.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/order")
    public ResponseEntity<ResponseObject> createOrder(HttpServletRequest request, @RequestBody OrderCreateDTO order) {
        System.out.println(order);
        return orderService.createOrder(request, order);
    }

    @GetMapping("/order/getOrders")
    public ResponseEntity<ResponseObject> getOrders(HttpServletRequest request) {
        System.out.println("get orders");
        return orderService.getOrders(request);
    }

}
