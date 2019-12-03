//package com.fmuke.learn.ssooauth2.security;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//import org.springframework.security.oauth2.client.OAuth2RestTemplate;
//import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
//import org.springframework.stereotype.Component;
//@Configuration
//@Component
//public class AdditionalSecurity {
//    public AdditionalSecurity(){ }
//    @Profile("secure")
//    @Configuration
//    @EnableConfigurationProperties
//    @EnableOAuth2Client
//    public static class Config {
//        private static final Logger LOGGER = LoggerFactory.getLogger(Config.class);
//        @Bean
//        @ConfigurationProperties(prefix = "security.oauth2.client")
//        ClientCredentialsResourceDetails clientCredentialsResourceDetails() {
//            LOGGER.info("*** Creating client credentials...");
//            ClientCredentialsResourceDetails clienCredit = new ClientCredentialsResourceDetails();
//            return clienCredit;
//        }
//        @Bean
//        OAuth2RestTemplate clientCredentialsRestTemplate() {
//            return new OAuth2RestTemplate(clientCredentialsResourceDetails());
//        }
//    }
//}
