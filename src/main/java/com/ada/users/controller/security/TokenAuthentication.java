package com.ada.users.controller.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.util.List;

public class TokenAuthentication extends AbstractAuthenticationToken {
    String token;
    String subject;
    List<String> roles;

    public TokenAuthentication(String token, String subject, List<String> roles) {
        super(null);
        this.token = token;
        this.subject = subject;
        this.roles = roles;
    }

    @Override
    public boolean isAuthenticated() {
       return !token.isEmpty() && !subject.isEmpty();
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getPrincipal() {
        return subject;
    }
}
