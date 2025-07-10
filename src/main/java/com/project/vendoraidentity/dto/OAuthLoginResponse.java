package com.project.vendoraidentity.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OAuthLoginResponse {
    private String name;
    private String email;
    private String profileImage;
    private String provider;
    private String role;
    private String token; // If you decide to issue JWT after OAuth login
}