package com.example.shopping.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenPayLoad {
    private String username;
    private String role;
}
