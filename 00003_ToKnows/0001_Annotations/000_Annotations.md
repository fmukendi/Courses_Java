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

* (7) ``` @Autowired ```  see 12

* (8) ``` @Override ```   see 12

* (9) ``` @Component ```
```java
@Component
public class UserFilterService {
    private static List<UserFilter> UserFilters = new ArrayList<>();
    private static int UserFiltersCount = 3;
    static {
        UserFilters.add(new UserFilter(1, "Adam", "pass1", new Date()));
        UserFilters.add(new UserFilter(2, "Eve", "pass2", new Date()));
        UserFilters.add(new UserFilter(3, "Jack", "pass3", new Date()));
    }

    public List<UserFilter> findAll() {
        return UserFilters;
    }
    public UserFilter save(UserFilter UserFilter) {
        if(UserFilter.getId() == null) {
            UserFilter.setId(++UserFiltersCount);
        }
        UserFilters.add(UserFilter);

        return UserFilter;
    }
}
```

* (10) ``` @Valid ```

* (11) ``` @ControllerAdvice ```

* (12) ``` @Configuration ```

* (12-1) ``` @ConfigurationProperties ```
```java 
@Configuration
@ConfigurationProperties("limits-service")
public class AppConfiguration {
    private int minimum;
    private int maximum;

    public int getMinimum() {
        return minimum;
    }

    public void setMinimum(int minimum) {
        this.minimum = minimum;
    }

    public int getMaximum() {
        return maximum;
    }

    public void setMaximum(int maximum) {
        this.maximum = maximum;
    }

    @Override
    public String toString() {
        return "LimitsConfiguration{" +
                "maximum=" + maximum +
                ", minimum=" + minimum +
                '}';
    }
}
```
```yml
spring.application.name=limits-service

limits-service.minimum=99
limits-service.maximum=9999

```

```java
@RestController
public class LimitsConfigurationController {

    @Autowired
    private AppConfiguration configuration;

    @GetMapping("/limits")
    public LimitsConfiguration retrieveLimintsFromConfigurations(){

        return  new LimitsConfiguration(configuration.getMaximum(), configuration.getMinimum());
    }

    @GetMapping("/limitsold")
    public LimitsConfiguration retrieveLimintsFromConfigurationsOld(){

        return  new LimitsConfiguration(1000, 1);
    }
    
    
}

```

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

* (14) ```@ApiModel @ApiModelProperty @Entity  @Id   @GeneratedValue    ```   
```java 
@ApiModel(description = "All details about the user")
@JsonIgnoreProperties(value ={"password"} )
@Entity  //JPA
public class User {

    @Id
    @GeneratedValue
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

* (14) ```@Repository```  
```java
@Repository
public interface UserRepository  extends JpaRepository<User, Integer> {

}
```

* (14) ```@ManyToOne    @OneToMany```  
```java

@ApiModel(description = "All details about the user")
@JsonIgnoreProperties(value ={"password"} )
@Entity
public class User {

    @Id
    @GeneratedValue
    private Integer id;

    @Size(min=2 , message="Name should have at least 2 characters")
    @ApiModelProperty(notes="Name should have at least 2 characters")
    private String name;

    @Past
    @ApiModelProperty(notes="Birth date should be in the past")
    private Date birthDate;

    @JsonIgnore
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Post> postList;


    protected User() {

    }
}

@Entity
public class Post {

    private Integer id;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", user=" + user +
                '}';
    }
}

```