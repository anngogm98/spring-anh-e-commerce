package com.example.authservice.service;


import com.example.authservice.dto.oauth.AuthenticationRequestDto;
import com.example.authservice.dto.oauth.AuthenticationResponseDto;

public interface AuthenticationService {
    AuthenticationResponseDto authenticate(AuthenticationRequestDto authenticationDto);
}
