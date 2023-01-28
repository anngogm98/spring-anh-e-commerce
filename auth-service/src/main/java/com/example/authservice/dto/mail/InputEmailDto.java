package com.example.authservice.dto.mail;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;


@Data
@Accessors(chain = true)
public class InputEmailDto {
    private String firstname;
    private String lastname;
    @NotEmpty
    @Email
    private String emailTo;
    @NotEmpty
    private String newPassword;
}
