package com.example.authservice.controller;

import com.example.authservice.common.Endpoints;
import com.example.authservice.dto.SignupDto;
import com.example.authservice.service.UserService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@Slf4j
@Validated
@RestController
@AllArgsConstructor
@Api(tags = "Users APIs")
public class UserController {
    private final UserService userService;
    @PostMapping(Endpoints.URL_SIGN_UP)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<SignupDto> signingNewUser(@Valid @RequestBody SignupDto signupDto) {
        return ResponseEntity.ok().body(userService.createUser(signupDto));
    }
}
