package com.mukeapps.rest.servicies.restfulwebservices.service;

import com.mukeapps.rest.servicies.restfulwebservices.model.UserFilter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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
    public UserFilter findOne(int id) {
        for(UserFilter UserFilter:UserFilters) {
            if(UserFilter.getId() == id) {
                return UserFilter;
            }
        }
        return null;
    }

    public UserFilter deleteById(int id) {
        Iterator<UserFilter> iterator = UserFilters.iterator();
        while(iterator.hasNext()) {
            UserFilter UserFilter = iterator.next();
            if(UserFilter.getId() == id) {
                iterator.remove();
                return UserFilter;
            }
        }
        return null;
    }


}