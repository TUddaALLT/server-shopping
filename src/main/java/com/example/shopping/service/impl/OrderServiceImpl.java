package com.example.shopping.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.shopping.entity.Account;
import com.example.shopping.entity.MyOrder;
import com.example.shopping.entity.OrderDetails;
import com.example.shopping.entity.Product;
import com.example.shopping.model.ResponseObject;
import com.example.shopping.model.dto.OrderCreateDTO;
import com.example.shopping.repository.AccountRepository;
import com.example.shopping.repository.OrderRepository;
import com.example.shopping.repository.ProductRepository;
import com.example.shopping.service.OrderService;
import com.example.shopping.utils.JwtTokenUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final AccountRepository accountRepository;
    private final ProductRepository productRepository;
    private final JwtTokenUtils jwtTokenUtil;

    @Override
    public ResponseEntity<ResponseObject> createOrder(HttpServletRequest request, OrderCreateDTO order) {

        String token = request.getHeader("Authorization").substring(6);
        Account acc = jwtTokenUtil.getAccountLogin(token);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        MyOrder myOrder = MyOrder.builder()
                .name(order.getName())
                .address(order.getAddress())
                .phoneNumber(order.getPhoneNumber())
                .totalPrice(BigDecimal.valueOf(order.getTotalPrice()))
                .orderDate(formatter.format(date).toString())
                .build();
        Set<Product> products = new HashSet<>();
        Set<MyOrder> myOrders = new HashSet<>(acc.getOrders());
        Set<OrderDetails> orderDetails = new HashSet<>();
        myOrders.add(myOrder);
        for (OrderDetails object : order.getProducts()) {
            Optional<Product> product = productRepository.findById(object.getId());
            if (product.isPresent()) {
                product.get().setOrder_products(myOrders);
                OrderDetails orddt = OrderDetails.builder()
                        .quantity(object.getQuantity())
                        .ID_product(object.getId())
                        .build();
                orddt.setMyOrder(myOrder);
                orderDetails.add(orddt);
                products.add(product.get());
            }

        }
        myOrder.setOrderDetails(orderDetails);
        myOrder.setProducts(products);
        myOrder = orderRepository.save(myOrder);
        myOrder.setAccount(acc);

        Set<MyOrder> orderProducts = new HashSet<>();
        orderProducts.add(myOrder);
        acc.setOrders(orderProducts);
        acc = accountRepository.save(acc);

        return ResponseEntity.ok().body(ResponseObject.builder().status("500").message("create order success")
                .data(orderProducts.toString())
                .build());
    }

    @Override
    public ResponseEntity<ResponseObject> getOrders(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(6);
        Account acc = jwtTokenUtil.getAccountLogin(token);

        if (acc != null) {
            Set<MyOrder> orders = acc.getOrders();
            return ResponseEntity.ok().body(ResponseObject.builder().status("500").message("get order success")
                    .data(orders.toString())
                    .build());
        }
        return null;
    }

}
