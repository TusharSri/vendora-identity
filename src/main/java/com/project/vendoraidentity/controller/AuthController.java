package com.project.vendoraidentity.controller;

import com.project.vendoraidentity.dto.LoginRequest;
import com.project.vendoraidentity.dto.OAuthLoginResponse;
import com.project.vendoraidentity.dto.RegisterRequest;
import com.project.vendoraidentity.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<OAuthLoginResponse> register(@Valid @RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public Mono<OAuthLoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }
}
