package com.mukeapps.rest.servicies.restfulwebservices.controller;
import com.mukeapps.rest.servicies.restfulwebservices.model.HelloWorldBean;
import org.springframework.web.bind.annotation.*;


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

    //hello-world/path-variable/in28minutes
    @GetMapping( path="/hello-world/path-variable/{name}" )
    public HelloWorldBean helloWorldPathVariable(@PathVariable String name) {
        return new HelloWorldBean(String.format("Hello World, %s", name));
    }
}
