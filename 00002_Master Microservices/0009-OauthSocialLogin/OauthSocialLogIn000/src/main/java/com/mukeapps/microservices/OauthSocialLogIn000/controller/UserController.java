package com.mukeapps.microservices.OauthSocialLogIn000.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class UserController {
    @RequestMapping("/user")
    public Principal user(Principal principal) {
        return principal;
    }

//    @RequestMapping({ "/user", "/me" })
//    public Map<String, String> user(Principal principal) {
//        Map<String, String> map = new LinkedHashMap<>();
//        map.put("name", principal.getName());
//        return map;
//    }
}
