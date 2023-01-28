package com.example.authservice.service;

import com.example.authservice.common.EmailType;
import com.example.authservice.dto.mail.InputEmailDto;

public interface MailService {
    void sendMail(EmailType emailType, InputEmailDto inputEmailDto);
}
