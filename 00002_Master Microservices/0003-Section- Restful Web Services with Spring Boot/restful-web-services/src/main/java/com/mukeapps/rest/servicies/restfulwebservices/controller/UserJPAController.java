package com.mukeapps.rest.servicies.restfulwebservices.controller;

import com.mukeapps.rest.servicies.restfulwebservices.Exception.UserNotFoundException;
import com.mukeapps.rest.servicies.restfulwebservices.model.User;
import com.mukeapps.rest.servicies.restfulwebservices.repository.UserRepository;
import com.mukeapps.rest.servicies.restfulwebservices.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class UserJPAController {
	/*
	 HATEOAS (Hypermedia as the Engine of Application State) is a constraint of
	 the REST application architecture. A hypermedia-driven site provides information
	 to navigate the site's REST interfaces dynamically by including hypermedia links with the responses.
	 */

    @Autowired
    private UserRepository userRepository;

    // GET /users
    // RetrieveAllUsers
    @GetMapping("/jpa/users")
    public List<User> retrieveAllUsers(){
        return userRepository.findAll();
    }

    //Get /jpa/users/{id}
    // RetrieveUser(int id)
    @GetMapping("/jpa/usersSimple/{id}")
    public User retrieveUserSimple(@PathVariable int id) {
        Optional<User> optional  = userRepository.findById(id);
        if(!optional.isPresent()) {
            throw new UserNotFoundException("id-"+id);
        }

        return optional.get();
    }

    @GetMapping("/jpa/users/{id}")
    public User retrieveUser(@PathVariable int id) {
        Optional<User> optional  = userRepository.findById(id);
        User user = optional.get();
        if(user == null) {
            throw new UserNotFoundException("id-"+id);
        }

        return user;
    }

    // HATEOS
    @GetMapping("/jpa/usershateos/{id}")
    public Resource<User> retrieveUserHateos(@PathVariable int id) {
        Optional<User> optional  = userRepository.findById(id);
        User user = optional.get();
        if(user == null) {
            throw new UserNotFoundException("id-"+id);
        }
        //"all-users", SERVER_PATH + "/users"
        // retrieveAllUsers
        Resource<User> resource = new Resource<>(user);
        ControllerLinkBuilder linkto =
                linkTo(methodOn(this.getClass()).retrieveAllUsers());

        resource.add(linkto.withRel("all-users"));

        return resource;
    }

    @DeleteMapping("/jpa/users/{id}")
    public void DeleterUser(@PathVariable int id) {
        Integer intObj = new Integer(id);
        userRepository.deleteById(intObj);
    }

    //Created
    // input -details of user
    // output- CREATED & Return the created URI
    //Response of 200 (completed)
    @PostMapping("/jpa/usersSimple")
    public void createUserSimple(@RequestBody User user) {
        User savedUser = userRepository.save(user);
    }

    //Created
    // input -details of user
    // output- CREATED & Return the created URI
    //Response of 201 (created)
    @PostMapping("/jpa/usersComplex")
    public ResponseEntity<Object> createUserComplex(@RequestBody User user)
    {
        User savedUser = userRepository.save(user);
        //CREATED
        // /user/{id}  savedUser.getId
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    // Validation
    @PostMapping("/jpa/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user)
    {
        User savedUser = userRepository.save(user);
        //CREATED
        // /user/{id}  savedUser.getId
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }



}
