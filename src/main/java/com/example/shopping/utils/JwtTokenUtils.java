package com.example.shopping.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.example.shopping.entity.Account;
import com.example.shopping.model.TokenPayLoad;
import com.example.shopping.repository.AccountRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtTokenUtils {
    private final AccountRepository accountRepository;
    private String secret = "tudda";

    public String generateToken(Account account, long time) {
        Map<String, Object> claims = new HashMap<String, Object>();
        TokenPayLoad payload = TokenPayLoad.builder().username(account.getUsername()).role("USER").build();
        claims.put("payload", payload);

        return Jwts.builder().setClaims(claims).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + time * 1000))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public TokenPayLoad getTokenPayload(String token) {
        return getClaimsFromToken(token, (Claims claims) -> {
            Map<String, Object> mapResult = (Map<String, Object>) claims.get("payload");
            return TokenPayLoad.builder()
                    .username((String) mapResult.get("username"))
                    // .role(mapResult.get("role").toString())
                    .build();
        });
    }

    private <T> T getClaimsFromToken(String token, Function<Claims, T> claimResolver) {
        final Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return claimResolver.apply(claims);
    }

    // public TokenPayLoad myGetTokenPayload(String token) {
    // final Claims claims =
    // Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    // Map<String, Object> mapResult = (Map<String, Object>) claims.get("payload");
    // return TokenPayLoad.builder()
    // .username((String) mapResult.get("username"))
    // .role(mapResult.get("role").toString())
    // .build();
    // }

    public Account getAccountLogin(String token) {
        TokenPayLoad payload = getTokenPayload(token);
        payload.setRole("USER");
        Optional<Account> account = accountRepository.findAccountByUsername(payload.getUsername());
        Account acc = null;
        if (account.isPresent()) {
            acc = account.get();
            return acc;
        } else {
            return null;
        }
    }
}
