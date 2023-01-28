package com.example.authservice.dto.oauth;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = AuthenticationRequestDto.TYPE)
@JsonSubTypes({
        @JsonSubTypes.Type(value = EmailAndPasswordAuthenticationDto.class, name = EmailAndPasswordAuthenticationDto.TYPE)
})
@Data
public abstract class AuthenticationRequestDto {

    static final String TYPE = "type";
}
