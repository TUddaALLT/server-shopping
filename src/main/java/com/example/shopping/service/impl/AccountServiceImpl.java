package com.example.shopping.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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

                Optional<Account> acc = accountRepository.findAccountByUsername(account.getUsername());

                if (acc.isPresent() && acc.get().getPassword().equals(account.getPassword())) {

                        return ResponseEntity.ok()
                                        .body(ResponseObject.builder().status("500").message("login successfully")
                                                        .data(AccountDTOResponse.builder()
                                                                        .username(acc.get().getUsername())
                                                                        .token(jwtTokenUtils.generateToken(acc.get(),
                                                                                        24 * 60 * 60))
                                                                        .build())
                                                        .build());

                }
                return ResponseEntity.ok().body(ResponseObject.builder().status("400").message("login failed")
                                .data(null)
                                .build());
        }

        @Override
        public ResponseEntity<ResponseObject> register(AccountDTORequest account) {
                List<Account> accounts = accountRepository.findAll();
                for (Account account2 : accounts) {
                        if (account.getUsername().equals(account2.getUsername())) {
                                return ResponseEntity.ok()
                                                .body(ResponseObject.builder().status("400").message("Username Exist")
                                                                .data(null)
                                                                .build());
                        }
                }
                if (!account.getUsername().contains("@")
                                || account.getPassword().length() < 8
                                || !account.getPassword().equals(account.getConfirm_password())) {
                        return ResponseEntity.ok()
                                        .body(ResponseObject.builder().status("400").message("input failed")
                                                        .data(null)
                                                        .build());
                } else {
                        Account reg = Account
                                        .builder()
                                        .username(account.getUsername())
                                        .password(account.getPassword())
                                        .role("USER")
                                        .build();
                        Account re = accountRepository.save(reg);

                        return ResponseEntity.ok()
                                        .body(ResponseObject.builder().status("500")
                                                        .message("register successfull")
                                                        .data(null)
                                                        .build());

                }
        }

}
