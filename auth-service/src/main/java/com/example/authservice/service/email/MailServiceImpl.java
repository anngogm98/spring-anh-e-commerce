package com.example.authservice.service.email;


import com.example.authservice.common.EmailType;
import com.example.authservice.dto.mail.InputEmailDto;
import com.example.authservice.error.SendEmailException;
import com.example.authservice.service.MailService;
import com.example.authservice.service.ThymeleafService;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.Map;

@Service
public class MailServiceImpl implements MailService {

    private static final String CONTENT_TYPE_TEXT_HTML = "text/html;charset=\"utf-8\"";

    private final ThymeleafService thymeleafService;
    private final JavaMailSender javaMailSender;
    private static Map<EmailType, String> subjectTitleMap = new EnumMap<>(EmailType.class);

    @Value("${spring.mail.username}")
    private String mailUsername;

    static {
        subjectTitleMap.put(EmailType.FORGOT_PASSWORD, "New Password For User");
        subjectTitleMap.put(EmailType.GENERATE_ACCOUNT_PASSWORD, "Generate Password For New Account");
    }

    public MailServiceImpl(ThymeleafService thymeleafService, JavaMailSender javaMailSender) {
        this.thymeleafService = thymeleafService;
        this.javaMailSender = javaMailSender;
    }

    @Override
    @Async
    public void sendMail(EmailType emailType, InputEmailDto inputEmailDTO) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(inputEmailDTO.getEmailTo()));
            message.setFrom(new InternetAddress(mailUsername));
            message.setSubject(subjectTitleMap.get(emailType));
            message.setContent(thymeleafService.getContent(emailType, inputEmailDTO), CONTENT_TYPE_TEXT_HTML);
        } catch (MessagingException e) {
            throw new SendEmailException("Have exception when send email");
        }
        javaMailSender.send(message);
    }

}
