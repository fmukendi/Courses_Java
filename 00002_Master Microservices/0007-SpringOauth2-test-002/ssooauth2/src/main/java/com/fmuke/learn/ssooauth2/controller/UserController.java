package com.fmuke.learn.ssooauth2.controller;

import java.security.Principal;
import java.util.Optional;

import com.fmuke.learn.ssooauth2.model.oauth.AccessTokenInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class UserController {
    @GetMapping("/user")
    public Principal user(Principal principal) {

        return principal;
    }

    @GetMapping("/token/v1")
    public Optional<String> getAccessToken() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            if (auth instanceof OAuth2Authentication) {
                OAuth2Authentication oauth = (OAuth2Authentication) auth;
                Object details = oauth.getDetails();
                if (details instanceof OAuth2AuthenticationDetails) {
                    OAuth2AuthenticationDetails oauth2Details = (OAuth2AuthenticationDetails) details;
                    return Optional.ofNullable(oauth2Details.getTokenValue());
                }
            }
        }
        return Optional.empty();
    }

    @GetMapping("/token/v2")
    public AccessTokenInfo getAccessTokenJson() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            if (auth instanceof OAuth2Authentication) {
                OAuth2Authentication oauth = (OAuth2Authentication) auth;
                Object details = oauth.getDetails();
                if (details instanceof OAuth2AuthenticationDetails) {
                    final OAuth2AuthenticationDetails oauth2Details = (OAuth2AuthenticationDetails) details;
                    return new AccessTokenInfo(AccessTokenInfo.TokenType.OAUTH2, oauth2Details.getTokenValue());
                }
            }
        }

        return  null;

    }
}

