package com.project.vendoraidentity.service;

import com.project.vendoraidentity.dto.LoginRequest;
import com.project.vendoraidentity.dto.OAuthLoginResponse;
import com.project.vendoraidentity.dto.RegisterRequest;
import reactor.core.publisher.Mono;

public interface AuthService {
    Mono<OAuthLoginResponse> register(RegisterRequest request);
    Mono<OAuthLoginResponse> login(LoginRequest request);
}
