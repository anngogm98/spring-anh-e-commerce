package com.example.authservice.dto.oauth;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AuthenticatedUser implements Authentication {
    private Long id;
    private String role;
    private String email;
    private String firstName;
    private String lastName;
}
