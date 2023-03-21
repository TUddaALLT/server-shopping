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
import com.example.shopping.model.dto.OrderDetailsResponseDTO;
import com.example.shopping.model.dto.OrderResponseDTO;
import com.example.shopping.model.dto.ProductResponseDTO;
import com.example.shopping.repository.AccountRepository;
import com.example.shopping.repository.OrderRepository;
import com.example.shopping.repository.ProductRepository;
import com.example.shopping.service.OrderService;
import com.example.shopping.utils.JwtTokenUtils;

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
        if(order.getName()==null||order.getAddress()==null || order.getPhoneNumber().length()<10 ){
            return ResponseEntity.ok().body(ResponseObject.builder().status("400").message("input is failed")
                    .data(null)
                    .build());

        }else{
            try {
                Integer.parseInt(order.getPhoneNumber());
            }catch (Exception e){
                return ResponseEntity.ok().body(ResponseObject.builder().status("400").message("number format ex")
                        .data(null)
                        .build());
            }
        }
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
        Set<ProductResponseDTO> resProducts = new HashSet<>();
        for (Product p : products) {
            ProductResponseDTO productResponseDTO = ProductResponseDTO
                    .builder()
                    .id(p.getId())
                    .img(p.getImg())
                    .author(p.getAuthor())
                    .name(p.getName())
                    .brand(p.getBrand())
                    .category(p.getCategory())
                    .suplier(p.getSuplier())
                    .original(p.getOriginal())
                    .price(p.getPrice())
                    .quantity(p.getQuantity())
                    .build();
            resProducts.add(productResponseDTO);
        }

        Set<OrderDetailsResponseDTO> resOrderDetailsResponseDTO = new HashSet<>();
        for (OrderDetails o : orderDetails) {
            OrderDetailsResponseDTO detailsResponseDTO = OrderDetailsResponseDTO
                    .builder()
                    .id(o.getId())
                    .quantity(o.getQuantity())
                    .ID_product(o.getID_product())
                    .feedback(o.getFeedback())
                    .price(o.getPrice())
                    .build();
            resOrderDetailsResponseDTO.add(detailsResponseDTO);
        }

        OrderResponseDTO orderDetailsResponseDTO = OrderResponseDTO
                .builder()
                .orderId(myOrder.getOrderId())
                .orderDate(myOrder.getOrderDate())
                .totalPrice(myOrder.getTotalPrice())
                .address(myOrder.getAddress())
                .phoneNumber(myOrder.getPhoneNumber())
                .name(myOrder.getName())
                .products(resProducts)
                .orderDetails(resOrderDetailsResponseDTO)
                .build();

        return ResponseEntity.ok().body(ResponseObject.builder().status("500").message("create order success")
                .data(orderDetailsResponseDTO)
                .build());
    }

    @Override
    public ResponseEntity<ResponseObject> getOrders(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(6);
        Account acc = jwtTokenUtil.getAccountLogin(token);

        if (acc != null) {
            Set<MyOrder> orders = acc.getOrders();

            Set<OrderResponseDTO> response = new HashSet<OrderResponseDTO>();

            for (MyOrder orderResponseDTO : orders) {
                Set<ProductResponseDTO> resProducts = new HashSet<>();
                for (Product p : orderResponseDTO.getProducts()) {
                    ProductResponseDTO productResponseDTO = ProductResponseDTO
                            .builder()
                            .id(p.getId())
                            .img(p.getImg())
                            .author(p.getAuthor())
                            .name(p.getName())
                            .brand(p.getBrand())
                            .category(p.getCategory())
                            .suplier(p.getSuplier())
                            .original(p.getOriginal())
                            .price(p.getPrice())
                            .quantity(p.getQuantity())
                            .build();
                    resProducts.add(productResponseDTO);
                }

                Set<OrderDetailsResponseDTO> resOrderDetailsResponseDTO = new HashSet<>();
                for (OrderDetails o : orderResponseDTO.getOrderDetails()) {
                    OrderDetailsResponseDTO detailsResponseDTO = OrderDetailsResponseDTO
                            .builder()
                            .id(o.getId())
                            .quantity(o.getQuantity())
                            .ID_product(o.getID_product())
                            .feedback(o.getFeedback())
                            .price(o.getPrice())
                            .build();
                    resOrderDetailsResponseDTO.add(detailsResponseDTO);
                }

                OrderResponseDTO orderDetailsResponseDTO = OrderResponseDTO
                        .builder()
                        .orderId(orderResponseDTO.getOrderId())
                        .orderDate(orderResponseDTO.getOrderDate())
                        .totalPrice(orderResponseDTO.getTotalPrice())
                        .address(orderResponseDTO.getAddress())
                        .phoneNumber(orderResponseDTO.getPhoneNumber())
                        .name(orderResponseDTO.getName())
                        .products(resProducts)
                        .orderDetails(resOrderDetailsResponseDTO)
                        .build();
                response.add(orderDetailsResponseDTO);
            }

            return ResponseEntity.ok().body(ResponseObject.builder().status("500").message("get order success")
                    .data(response)
                    .build());
        }
        return null;
    }

    @Override
    public ResponseEntity<ResponseObject> getOrdersById(HttpServletRequest request, int id) {
        String token = request.getHeader("Authorization").substring(6);
        Account acc = jwtTokenUtil.getAccountLogin(token);

        if (acc != null) {
            Set<MyOrder> orders = acc.getOrders();
            orders.removeIf((MyOrder o) -> !(o.getOrderId()==id));
            Set<OrderResponseDTO> response = new HashSet<OrderResponseDTO>();

            for (MyOrder orderResponseDTO : orders) {
                Set<ProductResponseDTO> resProducts = new HashSet<>();
                for (Product p : orderResponseDTO.getProducts()) {
                    ProductResponseDTO productResponseDTO = ProductResponseDTO
                            .builder()
                            .id(p.getId())
                            .img(p.getImg())
                            .author(p.getAuthor())
                            .name(p.getName())
                            .brand(p.getBrand())
                            .category(p.getCategory())
                            .suplier(p.getSuplier())
                            .original(p.getOriginal())
                            .price(p.getPrice())
                            .quantity(p.getQuantity())
                            .build();
                    resProducts.add(productResponseDTO);
                }

                Set<OrderDetailsResponseDTO> resOrderDetailsResponseDTO = new HashSet<>();
                for (OrderDetails o : orderResponseDTO.getOrderDetails()) {
                    OrderDetailsResponseDTO detailsResponseDTO = OrderDetailsResponseDTO
                            .builder()
                            .id(o.getId())
                            .quantity(o.getQuantity())
                            .ID_product(o.getID_product())
                            .feedback(o.getFeedback())
                            .price(o.getPrice())
                            .build();
                    resOrderDetailsResponseDTO.add(detailsResponseDTO);
                }

                OrderResponseDTO orderDetailsResponseDTO = OrderResponseDTO
                        .builder()
                        .orderId(orderResponseDTO.getOrderId())
                        .orderDate(orderResponseDTO.getOrderDate())
                        .totalPrice(orderResponseDTO.getTotalPrice())
                        .address(orderResponseDTO.getAddress())
                        .phoneNumber(orderResponseDTO.getPhoneNumber())
                        .name(orderResponseDTO.getName())
                        .products(resProducts)
                        .orderDetails(resOrderDetailsResponseDTO)
                        .build();
                response.add(orderDetailsResponseDTO);
            }
            if(response.size()==0){
                return ResponseEntity.ok().body(ResponseObject.builder().status("400").message("Order id is not existed")
                        .data(null)
                        .build());
            }
            return ResponseEntity.ok().body(ResponseObject.builder().status("500").message("get order success")
                    .data(response)
                    .build());
        }
        return null;
    }

}
