package com.project.vendoraidentity.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private String id;

    private String name;

    private String email;

    // Password is only required for LOCAL users
    private String password;

    // LOCAL or GOOGLE
    private String authProvider;

    // USER, ADMIN, etc.
    private String role;

    // Optional: Google profile image
    private String profileImage;
}
