package com.fmuke.learn.ssooauth2.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;
@Configuration
@Component
@Slf4j
public class Security {
    public Security() {
    }
    @Profile({"default", "nonsecure"})
    @Configuration
    @EnableWebSecurity
    public static class CustomWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
        public CustomWebSecurityConfigurerAdapter() {
        }
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            log.info("***** Disabling security for this microservice...");
//            http.csrf().disable().authorizeRequests().antMatchers("/**").permitAll();
            http.authorizeRequests().antMatchers("/", "/user", "/login**", "/error**").permitAll().anyRequest()
                    .authenticated().and().logout().logoutSuccessUrl("/").permitAll();
            http.csrf().disable();
        }
//        @Override
//        public void configure(WebSecurity web) {
//            web.ignoring().antMatchers(HttpMethod.OPTIONS);
//        }
    }
    @Profile("secure")
    @Configuration
    @EnableResourceServer
    public static class ResourceServerConfig extends ResourceServerConfigurerAdapter {
        @Autowired
        private ResourceLoader resourceLoader;
        @Autowired
        private ClientCredentialsResourceDetails clientCredentialsResourceDetails;
        /**
         * These are URL patterns for API endpoints that DO NOT require
         * authentication.
         */
        private static final String[] UNAUTHENTICATED_URL_PATTERNS = {
                // -- Error endpoint
                "/error/**",
                // -- Trace endpoint
                "/trace/**",
                "/env/**",
                "/httptrace/**",
                // -- Swagger endpoints
                "/swagger-ui.html",
                "/swagger/**",
                "/swagger-resources/**",
                "/v2/api-docs",
                // -- Pivotal endpoints
                "/cloudfoundryapplication/**",
                "/actuator/**",
                "/webjars/**",
                "/stores/**",
                "/",
                "/login",
                "/user",
                "favicon.ico"
        };
        @Override
        public void configure(HttpSecurity http) throws Exception {
            log.info("***** Enabling security for this microservice...");
            http
                    .authorizeRequests()
                    .antMatchers(UNAUTHENTICATED_URL_PATTERNS).permitAll()
                    .antMatchers("/**").authenticated();
        }
        @Override
        public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
            resources.resourceId("openid");
        }
    }
//    public static void dumpHeaders(final String methodName, final HttpHeaders headers) {
//        log.info("**** [{}] Here are the headers that were passed in:", methodName);
//        headers.entrySet().stream().forEach(e -> {
//            final String name = e.getKey();
//            final String values = e.getValue() == null ? "" : e.getValue().stream().collect(Collectors.joining("."));
//            log.info("**** [{}] Got header [{}] = [{}]", methodName, name, values);
//        });
//        log.info("**** [{}] End of headers.", methodName);
//    }
//    public static String getAuthorizationToken(final HttpHeaders headers) {
//        return headers.getFirst(HttpHeaders.AUTHORIZATION);
//    }
//    public static HttpHeaders createAuthorizationHeader(final HttpHeaders headers) {
//        final String authHeader = Security.getAuthorizationToken(headers);
//        HttpHeaders headersUpdated =  new HttpHeaders();
//        headersUpdated.set(HttpHeaders.AUTHORIZATION, authHeader);
//        return headersUpdated;
//    }
}