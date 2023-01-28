package com.example.authservice.service;


import com.example.authservice.common.EmailType;
import com.example.authservice.dto.mail.InputEmailDto;

public interface ThymeleafService {
    String getContent(EmailType emailType, InputEmailDto inputEmailDTO);
}
