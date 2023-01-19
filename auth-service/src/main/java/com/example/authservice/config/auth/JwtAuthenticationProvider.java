package com.example.authservice.config.auth;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public interface JwtAuthenticationProvider {

    AbstractAuthenticationToken get(String jwt);

}
