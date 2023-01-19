package com.example.authservice.config.auth.impl;

import com.example.authservice.config.auth.JwtAuthenticationProvider;
import com.example.authservice.config.auth.PublicKeyProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;

import static org.springframework.util.StringUtils.isEmpty;

@RequiredArgsConstructor
@Slf4j
public class LegacyJwtAuthenticationProvider implements JwtAuthenticationProvider {

    private static final String CLAIM_USER_ROLE = "role";
    private static final String CLAIM_KEY_USERNAME = "user_name";
    private static final String CLAIM_KEY_USER_ID = "userId";
    private static final String CLAIM_KEY_JTI = "jti";

    private final PublicKeyProvider publicKeyProvider;
//    private final BlackListTokenRepository blackListTokenRepository;

    @Override
    public AbstractAuthenticationToken get(String jwt) {
        Claims claims = getClaimsFromToken(jwt);
        String username = claims.get(CLAIM_KEY_USERNAME, String.class);
        String role = claims.get(CLAIM_USER_ROLE, String.class);
        String userId = claims.get(CLAIM_KEY_USER_ID, String.class);
        String jti = claims.get(CLAIM_KEY_JTI, String.class);

        if (isEmpty(username)) {
            throw new IllegalArgumentException("username claim must be present");
        }

//        if (blackListTokenRepository.findById(jti).isPresent()) {
//            throw new TokenHasExpiredException("Token has expired");
//        }

        CustomUserDetails userDetails = new CustomUserDetails()
                .setUserId(userId)
                .setEmail(username)
                .setRole(role);

        return new UsernamePasswordAuthenticationToken(
                userDetails, null, AuthorityUtils.createAuthorityList(role));
    }

    private Claims getClaimsFromToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(publicKeyProvider.getPublicKey())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            throw new IllegalArgumentException("Wrong token");
        }
    }

}
