package com.mukeapps.rest.servicies.restfulwebservices.repository;

import com.mukeapps.rest.servicies.restfulwebservices.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository  extends JpaRepository<User, Integer> {

}
