package com.project.vendoraidentity.dto;

import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
public class RegisterRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Email is invalid")
    private String email;

    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
}