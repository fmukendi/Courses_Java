package com.mukeapps.rest.servicies.restfulwebservices.repository;

import com.mukeapps.rest.servicies.restfulwebservices.model.Post;
import com.mukeapps.rest.servicies.restfulwebservices.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

}
