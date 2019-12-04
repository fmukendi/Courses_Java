package com.fmuke.learn.ssooauth2.model.oauth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.lang.invoke.MethodHandles;
import java.util.Optional;
import java.util.logging.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

/**
 *
 * @author kxm8bmb
 */
public class AccessTokenInfo {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public enum TokenType {
        UNKNOWN,
        OAUTH2
    };

    private final TokenType tokenType;

    private final String accessToken;

    public AccessTokenInfo(final TokenType tokenType, final String accessToken) {
        this.tokenType = tokenType;
        this.accessToken = accessToken;
    }

    public boolean isOAuth2() {
        return this.tokenType == TokenType.OAUTH2;
    }

    public boolean hasAccessToken() {
        return getAccessToken().isPresent();
    }

    public Optional<String> getAccessToken() {
        if (this.accessToken != null && this.accessToken.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(accessToken);
    }

    public Optional<String> getAccessTokenJson(final String ldap) {
        if (this.accessToken != null && this.accessToken.isEmpty()) {
            return Optional.empty();
        }

        final AccessToken token = new AccessToken(ldap, this.accessToken);
        try {
            final String json = MAPPER.writeValueAsString(token);
            return Optional.ofNullable(json);
        } catch (JsonProcessingException ex) {
            return Optional.empty();
        }
    }

    @Override
    public String toString() {
        return "AccessTokenInfo{" + "tokenType=" + tokenType + ", accessToken=" + accessToken + '}';
    }

    public static AccessTokenInfo of(final String user, final Authentication auth) {
        if (auth == null) {
            LOGGER.info("User [{}] does not have authentication.", user);
            return new AccessTokenInfo(TokenType.UNKNOWN, null);
        }
        if (!auth.isAuthenticated()) {
            LOGGER.info("User [{}] is not authenticated.", user);
            return new AccessTokenInfo(TokenType.UNKNOWN, null);
        }
        if (!(auth instanceof OAuth2Authentication)) {
            LOGGER.info("User [{}] is authenticated, but not with OAuth2. Authentication = [{}], name = [{}], auth principal = [{}]",
                    user, auth.getClass().getName(), auth.getName(), auth.getPrincipal());
            return new AccessTokenInfo(TokenType.UNKNOWN, null);
        }

        final Object details = auth.getDetails();
        if (details == null) {
            LOGGER.info("User [{}] is authenticated, but does not have authentication details", user);
            return new AccessTokenInfo(TokenType.UNKNOWN, null);
        }
        if (!(details instanceof OAuth2AuthenticationDetails)) {
            LOGGER.info("User [{}] is authenticated, but with authentication details of type {}",
                    user, details.getClass().getName());
            return new AccessTokenInfo(TokenType.OAUTH2, null);
        }

        final OAuth2AuthenticationDetails oauth2Details = (OAuth2AuthenticationDetails) details;
        return new AccessTokenInfo(TokenType.OAUTH2, oauth2Details.getTokenValue());
    }

    public static final class AccessToken {

        @JsonProperty("ldap")
        private final String ldap;

        @JsonProperty("token")
        private final String token;

        public AccessToken(final String ldap, final String token) {
            this.ldap = ldap;
            this.token = token;
        }
    }
}
