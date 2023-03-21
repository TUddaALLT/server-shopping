package com.example.shopping.service;

import org.springframework.http.ResponseEntity;

import com.example.shopping.model.ResponseObject;
import com.example.shopping.model.dto.AccountDTORequest;

public interface AccountService {

    ResponseEntity<ResponseObject> login(AccountDTORequest account);

    ResponseEntity<ResponseObject> register(AccountDTORequest account);

}
