package com.example.shopping.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    // private final JwtTokenUtil jwtTokenUtil;
    // private final AccountRepository accountRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // final String requestTokenHeader = request.getHeader("Authorization");
        // String token = null;
        // TokenPayLoad payload = null;

        // if (requestTokenHeader != null && requestTokenHeader.startsWith("Token")) {
        // token = requestTokenHeader.substring(6);
        // try {

        // payload = jwtTokenUtil.myGetTokenPayload(token);
        // } catch (SignatureException e) {
        // System.out.println("Invalid token");
        // } catch (IllegalArgumentException e) {
        // System.out.println("Unable to get token");
        // } catch (ExpiredJwtException e) {
        // System.out.println("Token has expired");
        // }

        // } else {
        // System.out.println("not token");
        // }
        // if (payload != null && SecurityContextHolder.getContext().getAuthentication()
        // == null) {
        // Optional<Account> accountOptional =
        // accountRepository.findByUsername(payload.getUsername());
        // if (accountOptional.isPresent()) {
        // Account account = accountOptional.get();
        // List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        // // if (jwtTokenUtil.validate(token, user)) {
        // // cap quyen cho user
        // // authorities.add(new SimpleGrantedAuthority("xxx"));
        // UserDetails userDetails = new
        // org.springframework.security.core.userdetails.User(account.getUsername(),
        // account.getPassword(), authorities);
        // UsernamePasswordAuthenticationToken userNamePassword = new
        // UsernamePasswordAuthenticationToken(
        // userDetails, null, authorities);
        // SecurityContextHolder.getContext().setAuthentication(userNamePassword);
        // // }
        // }
        // }
        // System.out.println(payload);
        filterChain.doFilter(request, response);

    }
}
