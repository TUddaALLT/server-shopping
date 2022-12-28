package com.example.shopping.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.shopping.model.ResponseObject;
import com.example.shopping.model.dto.AccountDTORequest;
import com.example.shopping.service.AccountService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000")
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/login")
    public ResponseEntity<ResponseObject> login(@RequestBody AccountDTORequest account) {

        return accountService.login(account);
    }

    // @PostMapping("/register")
    // public ResponseEntity<ResponseObject> register(@RequestBody AccountDTORequest
    // account) {
    // return accountService.register(account);
    // }

    // @PostMapping("/account/update")
    // public ResponseEntity<ResponseObject> updateAccount(HttpServletRequest
    // request) {
    // String token = request.getHeader("Authorization");
    // token = token.substring(6);
    // return accountService.updateAccount(token);
    // }
}
