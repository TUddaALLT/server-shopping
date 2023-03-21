package com.example.shopping.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.shopping.model.ResponseObject;
import com.example.shopping.model.dto.OrderCreateDTO;
import com.example.shopping.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/order")
    public ResponseEntity<ResponseObject> createOrder(HttpServletRequest request, @RequestBody OrderCreateDTO order) {
        System.out.println(order);
        return orderService.createOrder(request, order);
    }

    @GetMapping("/myorder")
    public ResponseEntity<ResponseObject> getOrders(HttpServletRequest request) {
        System.out.println("get orders");
        return orderService.getOrders(request);
    }
    @GetMapping("/myorder/{id}")
    public ResponseEntity<ResponseObject> getOrdersById(HttpServletRequest request , @PathVariable int id) {
        System.out.println("get orders");
        return orderService.getOrdersById(request, id);
    }

}
