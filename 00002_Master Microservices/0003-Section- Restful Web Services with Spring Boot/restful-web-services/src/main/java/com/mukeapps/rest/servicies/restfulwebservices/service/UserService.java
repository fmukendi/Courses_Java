package com.mukeapps.rest.servicies.restfulwebservices.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.mukeapps.rest.servicies.restfulwebservices.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserService {
    private static List<User> users = new ArrayList<>();
    private static int usersCount = 3;
    static {
        users.add(new User(1, "Adam", "pass1", new Date()));
        users.add(new User(2, "Eve", "pass2", new Date()));
        users.add(new User(3, "Jack", "pass3", new Date()));
    }

    public List<User> findAll() {
        return users;
    }
    public User save(User user) {
        if(user.getId() == null) {
            user.setId(++usersCount);
        }
        users.add(user);

        return user;
    }
    public User findOne(int id) {
        for(User user:users) {
            if(user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    public User deleteById(int id) {
        Iterator<User> iterator = users.iterator();
        while(iterator.hasNext()) {
            User user = iterator.next();
            if(user.getId() == id) {
                iterator.remove();
                return user;
            }
        }
        return null;
    }


}