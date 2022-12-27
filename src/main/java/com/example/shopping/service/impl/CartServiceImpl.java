package com.example.shopping.service.impl;

import java.util.Set;
import java.util.HashSet;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.shopping.entity.Account;
import com.example.shopping.entity.CartDetails;
import com.example.shopping.entity.MyCart;
import com.example.shopping.entity.Product;
import com.example.shopping.model.ResponseObject;
import com.example.shopping.repository.AccountRepository;
import com.example.shopping.repository.CartRepository;
import com.example.shopping.repository.ProductRepository;
import com.example.shopping.service.CartService;
import com.example.shopping.utils.JwtTokenUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final AccountRepository accountRepository;
    private final JwtTokenUtils jwtTokenUtil;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    @Override
    public ResponseEntity<ResponseObject> addToCart(HttpServletRequest request, int id, int quantity) {

        String token = request.getHeader("Authorization").substring(6);
        Account acc = jwtTokenUtil.getAccountLogin(token);
        MyCart cart = acc.getCart();
        if (cart == null) {
            cart = new MyCart();
        }
        Set<Product> products = new HashSet<>();
        if (cart.getProducts() != null) {
            products = new HashSet<>(cart.getProducts());
        }

        Optional<Product> product = productRepository.findById(id);
        Set<MyCart> carts = new HashSet<>();
        carts.add(cart);

        // cart detail
        Set<CartDetails> cartDetail = new HashSet<>();
        if (cart.getCartDetails() != null) {
            cartDetail = new HashSet<>(cart.getCartDetails());
        }
        CartDetails xDetails = CartDetails.builder().productID(id).cart(cart).quantity(quantity).build();
        cartDetail.add(xDetails);
        if (product.isPresent()) {
            product.get().setCarts(carts);
            System.out.println(product.get().toString());
            products.add(product.get());
        }
        cart.setProducts(products);
        cart.setCartDetails(cartDetail);
        cartRepository.save(cart);
        acc.setCart(cart);
        acc = accountRepository.save(acc);

        return ResponseEntity.ok().body(ResponseObject.builder().status("500").message("add to cart success")
                .data(acc.getCart().toString())
                .build());
    }

    @Override
    public ResponseEntity<ResponseObject> getMyCart(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(6);
        Account acc = jwtTokenUtil.getAccountLogin(token);
        if (acc.getCart() != null) {
            return ResponseEntity.ok().body(ResponseObject.builder().status("500").message("add to cart success")
                    .data(acc.getCart().toString())
                    .build());
        }
        return ResponseEntity.ok().body(ResponseObject.builder().status("400").message("Cart is empty")
                .data(null)
                .build());
    }

}
