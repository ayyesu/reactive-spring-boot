package com.ayyesu.reactiveprogramming.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class AuthFilter implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();

        // 👇 Allow unauthenticated access to log in and register endpoints
        if (path.equals("/login") || path.equals("signup")){
            return chain.filter(exchange); // ✅ Allow through
        }
        String token = exchange.getRequest().getHeaders().getFirst("Authorization");
        System.out.println(token);
        if (token == null || !token.startsWith("Bearer ")){
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            System.out.println("Interceptor rejected the request");
            return exchange.getResponse().setComplete(); // ❌ Don't go to the controller
        }
        return chain.filter(exchange); // ✅ Allow through
    }
}
