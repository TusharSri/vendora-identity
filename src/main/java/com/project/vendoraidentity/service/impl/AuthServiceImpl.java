package com.project.vendoraidentity.service.impl;

import com.project.vendoraidentity.dto.LoginRequest;
import com.project.vendoraidentity.dto.OAuthLoginResponse;
import com.project.vendoraidentity.dto.RegisterRequest;
import com.project.vendoraidentity.entity.User;
import com.project.vendoraidentity.exception.InvalidCredentialsException;
import com.project.vendoraidentity.exception.UserAlreadyExistsException;
import com.project.vendoraidentity.repository.UserRepository;
import com.project.vendoraidentity.service.AuthService;
import com.project.vendoraidentity.service.JwtService;
import com.project.vendoraidentity.utils.AuthProvider;
import com.project.vendoraidentity.utils.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Mono<OAuthLoginResponse> register(RegisterRequest request) {
        return userRepository.findByEmail(request.getEmail())
                .flatMap(existingUser -> Mono.error(new UserAlreadyExistsException("Email already registered"))
                        .cast(OAuthLoginResponse.class))
                .switchIfEmpty(Mono.defer(() -> {
                    String hashedPassword = passwordEncoder.encode(request.getPassword());

                    User newUser = User.builder()
                            .name(request.getName())
                            .email(request.getEmail())
                            .password(hashedPassword)
                            .authProvider(AuthProvider.LOCAL.name())
                            .role(UserRole.USER.name())
                            .build();

                    return userRepository.save(newUser)
                            .map(savedUser -> OAuthLoginResponse.builder()
                                    .name(savedUser.getName())
                                    .email(savedUser.getEmail())
                                    .provider(savedUser.getAuthProvider())
                                    .role(savedUser.getRole())
                                    .token(jwtService.generateToken(savedUser))
                                    .build());
                }));
    }

    @Override
    public Mono<OAuthLoginResponse> login(LoginRequest request) {
        return userRepository.findByEmail(request.getEmail())
                .switchIfEmpty(Mono.error(new InvalidCredentialsException("Invalid email or password")))
                .flatMap(user -> {
                    if (!AuthProvider.LOCAL.name().equals(user.getAuthProvider())) {
                        return Mono.error(new InvalidCredentialsException("Please login using " + user.getAuthProvider()));
                    }

                    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                        return Mono.error(new InvalidCredentialsException("Invalid email or password"));
                    }

                    return Mono.just(OAuthLoginResponse.builder()
                            .name(user.getName())
                            .email(user.getEmail())
                            .role(user.getRole())
                            .provider(user.getAuthProvider())
                            .token(jwtService.generateToken(user))
                            .build());
                });
    }
}

