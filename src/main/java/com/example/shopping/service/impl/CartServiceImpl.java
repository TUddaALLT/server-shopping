package com.example.shopping.service.impl;

import java.util.Set;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.shopping.entity.Account;
import com.example.shopping.entity.CartDetails;
import com.example.shopping.entity.MyCart;
import com.example.shopping.entity.Product;
import com.example.shopping.model.ResponseObject;
import com.example.shopping.model.dto.CartDetailsResponseDTO;
import com.example.shopping.model.dto.MyCartResponseDTO;
import com.example.shopping.model.dto.ProductResponseDTO;
import com.example.shopping.repository.AccountRepository;
import com.example.shopping.repository.CartDetailsRepository;
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
    private final CartDetailsRepository cartDetailsRepository;

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
        if (!product.isPresent()) {
            return ResponseEntity.ok().body(ResponseObject.builder().status("400").message("product is not exist")
                    .data(null)
                    .build());

        }
        Set<MyCart> carts = new HashSet<>();
        carts.add(cart);

        // cart detail
        Set<CartDetails> cartDetail = new HashSet<>();
        if (cart.getCartDetails() != null) {
            cartDetail = new HashSet<>(cart.getCartDetails());
        }
        int checkIsExist = 0;
        for (CartDetails cartDetails : cartDetail) {
            if (cartDetails.getProductID() == id) {
                System.out.println(cartDetails.getQuantity());
                cartDetails.setQuantity(quantity + cartDetails.getQuantity());
                cartDetail.add(cartDetails);
                checkIsExist = 1;
            }
        }
        if (checkIsExist == 0) {
            CartDetails xDetails = CartDetails.builder().productID(id).cart(cart).quantity(quantity).build();
            cartDetail.add(xDetails);
            if (product.isPresent()) {
                product.get().setCarts(carts);
                System.out.println(product.get().toString());
                products.add(product.get());
            }
        }
        BigDecimal totalPrice = BigDecimal.valueOf(0);

        try {
            for (CartDetails c : cartDetail) {
                for (Product p : products) {
                    if (c.getProductID() == p.getId()) {
                        totalPrice = totalPrice.add(
                                BigDecimal.valueOf(c.getQuantity()).multiply(p.getPrice()));
                    }
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        cart.setTotalPrice(totalPrice);
        cart.setProducts(products);
        cart.setCartDetails(cartDetail);
        cartRepository.save(cart);
        acc.setCart(cart);
        acc = accountRepository.save(acc);

        return ResponseEntity.ok().body(ResponseObject.builder().status("500").message("add to cart success")
                .data(null)
                .build());
    }

    @Override
    public ResponseEntity<ResponseObject> getMyCart(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(6);
        Account acc = jwtTokenUtil.getAccountLogin(token);

        Set<ProductResponseDTO> resProducts = new HashSet<>();
        Set<CartDetailsResponseDTO> rescCartDetailsResponseDTOs = new HashSet<>();
        try {
            for (CartDetails x : acc.getCart().getCartDetails()) {
                CartDetailsResponseDTO cartdeDetailsResponseDTO = CartDetailsResponseDTO
                        .builder()
                        .id(x.getId())
                        .quantity(x.getQuantity())
                        .productID(x.getProductID())
                        .build();
                rescCartDetailsResponseDTOs.add(cartdeDetailsResponseDTO);
            }
        } catch (Exception e) {
            return ResponseEntity.ok().body(ResponseObject.builder().status("400").message("Cart is empty")
                    .data(null)
                    .build());
        }

        for (Product p : acc.getCart().getProducts()) {
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

        List<ProductResponseDTO> res = new ArrayList<>();
        res.addAll(resProducts);
        Collections.sort(res, new Comparator<ProductResponseDTO>() {
            public int compare(ProductResponseDTO o1, ProductResponseDTO o2) {
                return o1.getId() - o2.getId();
            }
        });

        MyCartResponseDTO myResponse = MyCartResponseDTO.builder()
                .id(acc.getCart().getId())
                .totalPrice(acc.getCart().getTotalPrice())
                .products(res)
                .cartDetails(rescCartDetailsResponseDTOs)
                .build();

        if (acc.getCart() != null) {
            return ResponseEntity.ok().body(ResponseObject.builder().status("500").message("get my cart success")
                    .data(myResponse)
                    .build());
        }
        return ResponseEntity.ok().body(ResponseObject.builder().status("400").message("Cart is empty")
                .data(null)
                .build());
    }

    @Override
    public ResponseEntity<ResponseObject> updateCartDetails(HttpServletRequest request, int id, int quantity) {
        System.out.println("update quantity");
        Optional<CartDetails> cartDetails = cartDetailsRepository.findById(id);

        if (cartDetails.isPresent()) {
            cartDetails.get().setQuantity(quantity);
            cartDetailsRepository.save(cartDetails.get());
        }
        return null;
    }

}
