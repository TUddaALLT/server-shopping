package com.example.shopping.service.impl;

import java.util.Set;
import java.math.BigDecimal;
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
import com.example.shopping.model.dto.CartDetailsResponseDTO;
import com.example.shopping.model.dto.MyCartResponseDTO;
import com.example.shopping.model.dto.ProductResponseDTO;
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

        for (CartDetails c : cartDetail) {
            for (Product p : products) {
                if (c.getId() == p.getId()) {
                    totalPrice = totalPrice.add(
                            BigDecimal.valueOf(c.getQuantity()).multiply(p.getPrice()));
                }
            }
        }
        cart.setTotalPrice(totalPrice);
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

        Set<ProductResponseDTO> resProducts = new HashSet<>();
        Set<CartDetailsResponseDTO> rescCartDetailsResponseDTOs = new HashSet<>();
        for (CartDetails x : acc.getCart().getCartDetails()) {
            CartDetailsResponseDTO cartdeDetailsResponseDTO = CartDetailsResponseDTO
                    .builder()
                    .id(x.getId())
                    .quantity(x.getQuantity())
                    .productID(x.getProductID())
                    .build();
            rescCartDetailsResponseDTOs.add(cartdeDetailsResponseDTO);
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

        MyCartResponseDTO myResponse = MyCartResponseDTO.builder()
                .id(acc.getCart().getId())
                .totalPrice(acc.getCart().getTotalPrice())
                .products(resProducts)
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

}
