package com.example.shopping.service.impl;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.shopping.entity.Account;
import com.example.shopping.model.ResponseObject;
import com.example.shopping.model.dto.AccountDTORequest;
import com.example.shopping.model.dto.AccountDTOResponse;
import com.example.shopping.repository.AccountRepository;
import com.example.shopping.service.AccountService;
import com.example.shopping.utils.JwtTokenUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final JwtTokenUtils jwtTokenUtils;

    @Override
    public ResponseEntity<ResponseObject> login(AccountDTORequest account) {
        System.out.println("login xxx");
        Optional<Account> acc = accountRepository.findAccountByUsername(account.getUsername());
        System.out.println("login yyy");
        if (acc.isPresent()) {
            if (acc.get().getPassword().equals(account.getPassword())) {
                return ResponseEntity.ok().body(ResponseObject.builder().status("500").message("login successfully")
                        .data(AccountDTOResponse.builder().username(acc.get().getUsername())
                                .token(jwtTokenUtils.generateToken(acc.get(), 24 * 60 * 60)).build())
                        .build());
            }
        }
        return ResponseEntity.ok().body(ResponseObject.builder().status("400").message("login failed")
                .data(null)
                .build());
    }

}
