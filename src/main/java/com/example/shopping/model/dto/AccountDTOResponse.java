package com.example.shopping.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AccountDTOResponse {
    private String username;
    private String token;
}
