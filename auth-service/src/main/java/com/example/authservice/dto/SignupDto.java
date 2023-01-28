package com.example.authservice.dto;

import com.example.authservice.entity.User;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Slf4j
public class SignupDto {
    @NotEmpty
    @Size(max = 100)
    private String firstName;
    @NotEmpty
    @Size(max = 100)
    private String lastName;
    private Set<String> roles;
    @Email
    private String email;

    @NotEmpty
    private String password;

    private String avatar;

    private Boolean nonLocked;
    private Boolean enabled;
    private LocalDateTime creationTime;

}
