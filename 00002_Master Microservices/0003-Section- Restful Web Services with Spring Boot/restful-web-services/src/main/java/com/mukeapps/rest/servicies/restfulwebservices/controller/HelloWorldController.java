package com.mukeapps.rest.servicies.restfulwebservices.controller;
import com.mukeapps.rest.servicies.restfulwebservices.model.HelloWorldBean;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;


@RestController
public class HelloWorldController {

    /* @Autowired
	private MessageSource messageSource;
	*/

    //    @RequestMapping(method= RequestMethod.GET, path="/hello-world")
    @RequestMapping(method=RequestMethod.GET, path="/hello-world" )
    public String helloWorld() {
        return "Hello World";
    }

    @GetMapping( path="/hello-world2" )
    public String helloWorld2() {
        return "Hello World2";
    }

    @GetMapping( path="/hello-world-bean" )
    public HelloWorldBean helloWorldBean() {
        return new HelloWorldBean("Hello World3");
    }

    //hello-world/path-variable/in28minutes
    @GetMapping( path="/hello-world/path-variable/{name}" )
    public HelloWorldBean helloWorldPathVariable(@PathVariable String name) {
        return new HelloWorldBean(String.format("Hello World, %s", name));
    }

    @GetMapping( path="/hello-world-internationalized" )
    public String helloWorldInternationalized(Locale locale) {
        return "Good Morning International";
    }


	/*// need to work on it
	@GetMapping( path="/hello-world-internationalized" )
    public String helloWorldInternationalized(@RequestHeader(name="Accept-Language", required = false) Locale locale) {
    	return messageSource.getMessage("good.morning.message",null, locale);
    }*/
}
