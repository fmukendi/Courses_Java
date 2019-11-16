package com.mukeapps.rest.servicies.restfulwebservices.controller;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import javax.validation.Valid;

import com.mukeapps.rest.servicies.restfulwebservices.Exception.UserNotFoundException;
import com.mukeapps.rest.servicies.restfulwebservices.model.User;
import com.mukeapps.rest.servicies.restfulwebservices.service.UserDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserController {
	/*
	 HATEOAS (Hypermedia as the Engine of Application State) is a constraint of
	 the REST application architecture. A hypermedia-driven site provides information
	 to navigate the site's REST interfaces dynamically by including hypermedia links with the responses.
	 */

    @Autowired
    private UserDaoService service;
    // GET /users
    // RetrieveAllUsers
    @GetMapping("/users")
    public List<User> retrieveAllUsers(){
        return service.findAll();
    }

    //Get /users/{id}
    // RetrieveUser(int id)
    @GetMapping("/usersSimple/{id}")
    public User retrieveUserSimple(@PathVariable int id) {
        return service.findOne(id);
    }

    @GetMapping("/users/{id}")
    public User retrieveUser(@PathVariable int id) {
        User user = service.findOne(id);
        if(user == null) {
            throw new UserNotFoundException("id-"+id);
        }

        return user;
    }

    // HATEOS
    @GetMapping("/usershateos/{id}")
    public Resource<User> retrieveUserHateos(@PathVariable int id) {
        User user = service.findOne(id);
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

    @DeleteMapping("/users/{id}")
    public void DeleterUser(@PathVariable int id) {
        User user = service.deleteById(id);
        if(user == null) {
            throw new UserNotFoundException("id-"+id);
        }
    }

    //Created
    // input -details of user
    // output- CREATED & Return the created URI
    //Response of 200 (completed)
    @PostMapping("/usersSimple")
    public void createUserSimple(@RequestBody User user) {
        User savedUser = service.save(user);
    }

    //Created
    // input -details of user
    // output- CREATED & Return the created URI
    //Response of 201 (created)
    @PostMapping("/usersComplex")
    public ResponseEntity<Object> createUserComplex(@RequestBody User user)
    {
        User savedUser = service.save(user);
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
    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user)

    {
        User savedUser = service.save(user);
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
