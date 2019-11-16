package com.mukeapps.rest.servicies.restfulwebservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class restfulWebServicesApplication {

    public static void main(String[] args) {
        SpringApplication.run(restfulWebServicesApplication.class, args);
    }

	/*
	@Bean // Internationalization
	public LocalResolver localResolver() {
		SessionLocaleResolver localResolver = new SessionLocaleResolver();
		localResolver.setDefaultLocale(Locale.US);

		return localResolver;
	}

	@Bean
	public ResourceBundleMessageSource bundleMessageSource() {
		ResourceBundleMessageSource messageSource =
				new ResourceBundleMessageSource();
		messageSource.setBasename("messages");
		return messageSource;
	}
	*/
}
