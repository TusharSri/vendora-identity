package com.project.vendoraidentity.service;

import com.project.vendoraidentity.entity.User;

public interface JwtService {
    String generateToken(User user);

}
