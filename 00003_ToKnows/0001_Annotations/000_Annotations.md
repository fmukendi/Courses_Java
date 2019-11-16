Annotations
-----

* (0) ``` @Bean  ```

* (1) ``` @SpringBootApplication  ```
```java
@SpringBootApplication
public class restfulWebServicesApplication {

    public static void main(String[] args) {
        SpringApplication.run(restfulWebServicesApplication.class, args);
    }
}

```

* (2) ``` @RestController  ```

```java
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

* (3) ``` @RequestMapping  ```

```java

    @GetMapping(path = "/hello-world-ben")
    public HelloWorldBean helloWorldBean(){
        return new HelloWorldBean("HelloWorld");
    }
```

* (4) ``` @GetMapping , @PostMapping  ```


* (5) ``` @RequestBody  ```

* (6) ``` @PathVariable ```

* (7) ``` @Autowired ```

* (8) ``` @Override ```

* (9) ``` @Component ```

* (10) ``` @Valid ```

* (11) ``` @ControllerAdvice ```

* (12) ``` @Configuration ```

* (13) ``` @EnableSwagger2 ```

* (14) ``` @JsonIgnore ```
```java
public class User {

    private Integer id;

    @Size(min=2 , message="Name should have at least 2 characters")
    @ApiModelProperty(notes="Name should have at least 2 characters")
    private String name;

    @Past
    @ApiModelProperty(notes="Birth date should be in the past")
    private Date birthDate;

    @JsonIgnore
    private String password;


    protected User() {

    }
}
```

* (14) ```@JsonIgnoreProperties ```
```java
@JsonIgnoreProperties(value ={"password"} )
public class User {

    private Integer id;

    @Size(min=2 , message="Name should have at least 2 characters")
    @ApiModelProperty(notes="Name should have at least 2 characters")
    private String name;

    @Past
    @ApiModelProperty(notes="Birth date should be in the past")
    private Date birthDate;

    private String password;


    protected User() {

    }
}
```
* (14) ```@JsonFilter```
```java
@JsonIgnoreProperties(value ={"password"} )
@JsonFilter("filter_1")
public class User {

    private Integer id;

    @Size(min=2 , message="Name should have at least 2 characters")
    @ApiModelProperty(notes="Name should have at least 2 characters")
    private String name;

    @Past
    @ApiModelProperty(notes="Birth date should be in the past")
    private Date birthDate;

    @JsonIgnore
    private String password;


    protected User() {

    }
}

@RestController
public class UserController {

    @GetMapping("/users/dynamic-filtering")
    public MappingJacksonValue retrieveUsersDynamic() {
        List<User> userList = service.findAll();

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.
                                           filterOutAllExcept("birthDate","name");

        FilterProvider filters = new SimpleFilterProvider().addFilter("filter_1", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(userList);
        mapping.setFilters(filters);

        return mapping;
    }
}

```