package com.example.authservice.config.auth.impl;

import com.example.authservice.config.auth.PublicKeyProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.CertificateException;

@Component
@Slf4j
public class DefaultPublicKeyProvider implements PublicKeyProvider {
    private PublicKey publicKey;

//    public DefaultPublicKeyProvider(@Value("${spring.oauth.trustStorePath}") String trustStorePath,
//                                    @Value("${spring.oauth.trustStorePassword}") String trustStorePassword,
//                                    @Value("${spring.oauth.certificateAlias}") String certificateAlias)
//            throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException {
//        try (InputStream inputStream = new ClassPathResource(trustStorePath).getInputStream()) {
//            KeyStore keyStore = KeyStore.getInstance("JKS");
//            keyStore.load(inputStream, trustStorePassword.toCharArray());
//            this.publicKey = keyStore.getCertificate(certificateAlias).getPublicKey();
//        }
//    }

    @Override
    public PublicKey getPublicKey() {
        return publicKey;
    }
}
