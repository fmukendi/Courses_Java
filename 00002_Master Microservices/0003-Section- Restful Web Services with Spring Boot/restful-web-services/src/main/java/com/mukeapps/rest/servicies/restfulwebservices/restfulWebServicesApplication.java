package com.mukeapps.rest.servicies.restfulwebservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.apache.tomcat.util.descriptor.LocalResolver;

import java.util.Locale;

@SpringBootApplication
public class restfulWebServicesApplication {

    public static void main(String[] args) {
        SpringApplication.run(restfulWebServicesApplication.class, args);
    }


//	@Bean // Internationalization
//	public LocalResolver localResolver() {
//		SessionLocaleResolver localResolver = new SessionLocaleResolver();
//		localResolver.setDefaultLocale(Locale.US);
//
//		return localResolver;
//	}
//
//	@Bean
//	public ResourceBundleMessageSource bundleMessageSource() {
//		ResourceBundleMessageSource messageSource =
//				new ResourceBundleMessageSource();
//		messageSource.setBasename("messages");
//		return messageSource;
//	}
}
