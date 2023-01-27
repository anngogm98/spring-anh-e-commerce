package com.example.authservice.service;

import com.example.authservice.dto.SignupDto;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
@Service
public interface UserService {
    SignupDto createUser(@Valid SignupDto signupDto);

}
