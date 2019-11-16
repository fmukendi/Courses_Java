package com.mukeapps.rest.servicies.restfulwebservices.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.mukeapps.rest.servicies.restfulwebservices.Exception.UserNotFoundException;
import com.mukeapps.rest.servicies.restfulwebservices.model.User;
import com.mukeapps.rest.servicies.restfulwebservices.model.UserFilter;
import com.mukeapps.rest.servicies.restfulwebservices.service.UserFilterService;
import com.mukeapps.rest.servicies.restfulwebservices.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class UserFilterController {


    @Autowired
    private UserFilterService userFilterservice;

    @GetMapping("/usersfilter/dynamic-filtering")
    public MappingJacksonValue retrieveUsersDynamic() {
        List<UserFilter> userList = userFilterservice.findAll();

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.
                                           filterOutAllExcept("birthDate","name");

        FilterProvider filters = new SimpleFilterProvider().addFilter("filter_1", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(userList);
        mapping.setFilters(filters);

        return mapping;
    }

}
