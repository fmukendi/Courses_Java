package com.mukeapps.rest.servicies.restfulwebservices.controller;
import com.mukeapps.rest.servicies.restfulwebservices.model.HelloWorldBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloWorldController {

//    @RequestMapping(method= RequestMethod.GET, path="/hello-world")
    @GetMapping(path = "/hello-world")
    public String  helloWorld(){
        return "HelloWorld";
    }

    //hello-world-bean
    @GetMapping(path = "/hello-world-bean")
    public HelloWorldBean helloWorldBean(){
        return new HelloWorldBean("HelloWorld");
    }
}
