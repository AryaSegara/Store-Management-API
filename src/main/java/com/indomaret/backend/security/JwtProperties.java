package com.indomaret.backend.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties(prefix = "jwt")
@Data 
public class JwtProperties {
    private String secretKey;
    private long tokenExpiration;

}
