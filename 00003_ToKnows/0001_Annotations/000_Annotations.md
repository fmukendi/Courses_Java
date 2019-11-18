Annotations
-----

* (0) ``` @Bean  ```

* (1) ``` @SpringBootApplication  @EnableConfigServer @EnableFeignClients @FeignClient```
```java
@EnableConfigServer
@SpringBootApplication
public class restfulWebServicesApplication {

    public static void main(String[] args) {
        SpringApplication.run(restfulWebServicesApplication.class, args);
    }
}

```

```java

@SpringBootApplication
@EnableFeignClients("com.mukeapps.microservices.currencyconversionservice")
public class CurrencyConversionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurrencyConversionServiceApplication.class, args);
	}

}

@FeignClient(name ="currency-exchange-service", url = "http://localhost:8000")
public interface CurrencyExchangeServiceProxy {

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public ExchangeValue retrieveExchangeValue(@PathVariable("from") String from, @PathVariable("to") String to);
}

@RestController
public class CurrencyConversionController {

    @Autowired
    private CurrencyExchangeServiceProxy currencyExchangeServiceProxy;

    @GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversionBean convertCurrency(
            @PathVariable String from,
            @PathVariable String to,
            @PathVariable BigDecimal quantity
    ) {

        ExchangeValue response = currencyExchangeServiceProxy.retrieveExchangeValue(from, to);
        return new CurrencyConversionBean(response.getId(), from, to, response.getconversionMultiple(),
                quantity, quantity.multiply(response.getconversionMultiple()), response.getPort());
    }

    @GetMapping("/currency-converter-old/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversionBean convertCurrencyOld(
            @PathVariable String from,
            @PathVariable String to,
            @PathVariable BigDecimal quantity
    ) {

        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("from", from);
        uriVariables.put("to", to);

        ResponseEntity<CurrencyConversionBean> responseEntity = new RestTemplate().getForEntity(
                "http://localhost:8000/currency-exchange/from/USD/to/INR",
                CurrencyConversionBean.class,
                uriVariables
        );

        CurrencyConversionBean response = responseEntity.getBody();

//        return new CurrencyConversionBean(1L, from, to, BigDecimal.ONE, quantity, quantity, 8100);
        return new CurrencyConversionBean(response.getId(), from, to, response.getConversionMultiple(),
                quantity, quantity.multiply(response.getConversionMultiple()), response.getPort());
    }
}



```

* (1-1) ```@RibbonClient```
```java
//@FeignClient(name ="currency-exchange-service", url = "http://localhost:8000")
@FeignClient(name ="currency-exchange-service")
@RibbonClient(name ="currency-exchange-service" )
public interface CurrencyExchangeServiceProxy {

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public ExchangeValue retrieveExchangeValue(@PathVariable("from") String from, @PathVariable("to") String to);
}
```

```yml
spring.application.name=currency-conversion-service
server.port=8100
currency-exchange-service.ribbon.listOfServers=http://localhost:8000,http://localhost:8001
```

```xml

            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-starter-config</artifactId>
            </dependency>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-starter-feign</artifactId>
                <version>1.4.7.RELEASE</version>
            </dependency>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-starter-ribbon</artifactId>
                <version>1.4.7.RELEASE</version>
            </dependency>
```

* (1-2) ```@EnableEurekaServer  @EnableDiscoveryClient```
```java
@SpringBootApplication
@EnableEurekaServer
public class NetflixEurekaNamingServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(NetflixEurekaNamingServerApplication.class, args);
	}

}
```

```yml
spring.application.name=netflix-eureka-naming-server
server.port=8761
eureka.client.register-with-eureka=false
eureka.client-fetch-registry=false

```

```java
@SpringBootApplication
@EnableFeignClients("com.mukeapps.microservices.currencyconversionservice")
@EnableDiscoveryClient
public class CurrencyConversionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurrencyConversionServiceApplication.class, args);
	}

}
```

```yml
spring.application.name=currency-conversion-service
server.port=8100
eureka.client.service-url.default-zone=http://localhost:8761/eureka
currency-exchange-service.ribbon.listOfServers=http://localhost:8000,http://localhost:8001
```


* (1-3) ```@EnableZuulProxy EnableDiscoveryClient ```
```java
@EnableZuulProxy
@EnableDiscoveryClient
@SpringBootApplication
public class NetflixZuulApiGatewayServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(NetflixZuulApiGatewayServerApplication.class, args);
	}
}

```java
@Component
public class ZuulLoggingFilter extends ZuulFilter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
        logger.info("request -> {} request uri -> {}", request, request.getRequestURI());

        return null;
    }
}

//@FeignClient(name ="currency-exchange-service", url = "http://localhost:8000")
//@FeignClient(name ="currency-exchange-service")
@FeignClient(name = "netflix-zuul-api-gateway-server")
@RibbonClient(name ="currency-exchange-service" )
public interface CurrencyExchangeServiceProxy {

//    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    @GetMapping("/currency-exchange-service/currency-exchange/from/{from}/to/{to}")
    public ExchangeValue retrieveExchangeValue(@PathVariable("from") String from, @PathVariable("to") String to);
}


```yml
spring.application.name=netflix-zuul-api-gateway-server
server.port=8765
eureka.client.service-url.default-zone=http://localhost:8761/eureka
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

* (14) ```@ApiModel @ApiModelProperty @Entity  @Id   @GeneratedValue   @Column ```   
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

    @Column(name = "currency_from")
    private  String from;

    @Column(name = "currency_to")
    private  String to;


    protected User() {

    }
}
```

* (15) ```@Repository```  
```java
@Repository
public interface ExchangeValueRepository extends JpaRepository<ExchangeValue, Long> {

   ExchangeValue findByFromAndTo(String from, String to); // JPA is capable to implement this method on his own !!!
}
```

* (16) ```@ManyToOne    @OneToMany```  
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