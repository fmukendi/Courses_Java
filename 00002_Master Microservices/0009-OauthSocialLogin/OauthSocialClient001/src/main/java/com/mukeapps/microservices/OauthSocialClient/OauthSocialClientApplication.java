package com.mukeapps.microservices.OauthSocialClient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@EnableAutoConfiguration
@Configuration
@EnableOAuth2Sso
@RestController
public class OauthSocialClientApplication {

	@RequestMapping("/")
	public String home(Principal user) {
		return "Hello " + user.getName();
	}

//	public static void main(String[] args) {
//
//		SpringApplication.run(OauthSocialClientApplication.class, args);
//	}

	public static void main(String[] args) {
		new SpringApplicationBuilder(OauthSocialClientApplication.class)
				.properties("spring.config.name=client").run(args);
	}

}
