package com.example.authservice.dto.oauth;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;


@Data
@Accessors(chain = true)
public class EmailAndPasswordAuthenticationDto extends AuthenticationRequestDto {

    static final String TYPE = "emailAndPassword";

    @NotEmpty
    private String email;
    @NotEmpty
    private String password;
}
