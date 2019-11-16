Annotations
-----

* (0) @Bean

* (1) @SpringBootApplication
```
@SpringBootApplication
public class restfulWebServicesApplication {

    public static void main(String[] args) {
        SpringApplication.run(restfulWebServicesApplication.class, args);
    }
}

```

* (2) @RestController

```
@RestController
public class HelloWorldController {

//    @RequestMapping(method= RequestMethod.GET, path="/hello-world")
    @GetMapping(path = "/hello-world")
    public String  helloWorld(){
        return "HelloWorld";
    }

    //hello-world-bean
    @GetMapping(path = "/hello-world-ben")
    public HelloWorldBean helloWorldBean(){
        return new HelloWorldBean("HelloWorld");
    }
}
```

* (3) @RequestMapping

```

    @GetMapping(path = "/hello-world-ben")
    public HelloWorldBean helloWorldBean(){
        return new HelloWorldBean("HelloWorld");
    }
```

* (4) @GetMapping , @PostMapping


* (5) @RequestBody 

* (6) @PathVariable

* (7) @Autowired

* (8) @Override

* (9) @Component

* (10) @Valid

* (11) @ControllerAdvice