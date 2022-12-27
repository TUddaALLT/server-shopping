package com.example.shopping.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;

import com.example.shopping.model.ResponseObject;
import com.example.shopping.model.dto.OrderCreateDTO;

public interface OrderService {

    ResponseEntity<ResponseObject> createOrder(HttpServletRequest request, OrderCreateDTO order);

    ResponseEntity<ResponseObject> getOrders(HttpServletRequest request);

}
