package com.mukeapps.rest.servicies.restfulwebservices.configuration;

import java.util.HashSet;
import java.util.Set;

// import org.assertj.core.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//Configuration
@Configuration
// Enable Swagger
@EnableSwagger2
public class SwaggerConfig {

    public static final Contact DEFAULT_CONTACT = new Contact(
            "Franck Mukendi", "htt://www.mukeapps.com", "franck.mukendi@gmail.com");


    /*	private static final  Set<String> DEFAULT_PRODUCES_AND_CONSUMES =
                new HashSet<String>(Arrays.asList("application/json","application/xml"));// prefered method */
    private Set<String> DEFAULT_PRODUCES_AND_CONSUMES =
            new HashSet<String>();

    // @Bean
    // public Docket api() {
    // return new Docket(DocumentationType.SWAGGER_2);
    // }
    //
	/*@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(DEFAULT_API_INFO);
	}*/
    @Bean
    public Docket api() {
        DEFAULT_PRODUCES_AND_CONSUMES.add("application/json");
        DEFAULT_PRODUCES_AND_CONSUMES.add("application/xml");

        return new Docket(DocumentationType.SWAGGER_2)
                //.apiInfo(DEFAULT_API_INFO)
                .produces(DEFAULT_PRODUCES_AND_CONSUMES)
                .consumes(DEFAULT_PRODUCES_AND_CONSUMES);
    }
    // Bean - Docket
    // Swagger 2
    // All the paths
    // All the apis
}
