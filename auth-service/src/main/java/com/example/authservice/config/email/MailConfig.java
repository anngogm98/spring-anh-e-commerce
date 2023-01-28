package com.example.authservice.config.email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Value("${spring.mail.host}")
    private String mailHost;
    @Value("${spring.mail.username}")
    private String mailUsername;
    @Value("${spring.mail.password}")
    private String mailPassword;
    @Value("${spring.mail.port}")
    private String mailPort;
    @Value("${spring.mail.properties.mail.smtp.auth}")
    private String mailSmtpAuth;
    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private String mailSmtpStarttls;
    @Value("${spring.mail.protocol}")
    private String mailProtocol;
    @Value("${spring.mail.ssl.protocols}")
    private String mailSSLProtocols;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(mailHost);
        mailSender.setPort(Integer.parseInt(mailPort));
        mailSender.setUsername(mailUsername);
        mailSender.setPassword(mailPassword);
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", mailProtocol);
        props.put("mail.smtp.auth", mailSmtpAuth);
        props.put("mail.smtp.starttls.enable", mailSmtpStarttls);
        props.put("mail.smtp.ssl.protocols", mailSSLProtocols);
        return mailSender;
    }
}
