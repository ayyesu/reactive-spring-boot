package com.ayyesu.reactiveprogramming.controller;

import com.ayyesu.reactiveprogramming.config.JWTUtil;
import com.ayyesu.reactiveprogramming.model.AuthRequest;
import com.ayyesu.reactiveprogramming.model.AuthResponse;
import com.ayyesu.reactiveprogramming.model.User;
import com.ayyesu.reactiveprogramming.service.JWTAuthenticationManager;
import com.ayyesu.reactiveprogramming.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class AuthController {
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private JWTAuthenticationManager jwtAuthenticationManager;

    @PostMapping("/login")
    public Mono<ResponseEntity<AuthResponse>> login(@RequestBody AuthRequest authRequest) {
        return userService.findByUsername(authRequest.getUsername())
                .map(userDetails -> {
                    boolean matches = passwordEncoder.matches(authRequest.getPassword(), userDetails.getPassword());
                    if (matches) {
                        return ResponseEntity.ok(new AuthResponse(jwtUtil.generateToken(authRequest.getUsername())));
                    } else {
                        throw new BadCredentialsException("Invalid username or password!!");
                    }
                })
                .switchIfEmpty(Mono.error(new BadCredentialsException("Invalid username or password")));
    }

    @PostMapping("/signup")
    public Mono<ResponseEntity<String>> signup(@RequestBody User user){
        //Encrypt password before saving
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userService.save(user)
                .map(savedUser -> ResponseEntity.ok("Account created successfully.."));
    }

    @GetMapping("/protected")
    public Mono<ResponseEntity<String>> protectedEndpoint(){
       return Mono.just(ResponseEntity.ok("You have accessed a protected endpoint!!!"));
    }
}

