package com.example.authservice.dto.oauth;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class AuthenticationResponseDto {
    private String scope;
    private String loginMethod;
    private Authentication user;
    private List<String> features;
}
