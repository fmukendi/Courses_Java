package com.mukeapps.rest.servicies.restfulwebservices.controller;

import com.mukeapps.rest.servicies.restfulwebservices.Exception.UserNotFoundException;
import com.mukeapps.rest.servicies.restfulwebservices.model.Post;
import com.mukeapps.rest.servicies.restfulwebservices.model.User;
import com.mukeapps.rest.servicies.restfulwebservices.repository.PostRepository;
import com.mukeapps.rest.servicies.restfulwebservices.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class PostJPAController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    // GET /users
    // RetrieveAllUsers
    @GetMapping("/jpa/users/{id}/posts")
    public List<Post> retrieveAllPostsByUser(@PathVariable int id){

        Optional<User> userOptional  = userRepository.findById(id);
        if(!userOptional.isPresent()) {
            throw new UserNotFoundException("id-"+id);
        }

        return userOptional.get().getPostList();
    }

    // Validation
    @PostMapping("/jpa/users/{id}/posts")
    public ResponseEntity<Object> createPost(@PathVariable int id, @RequestBody Post post)
    {
        Optional<User> userOptional  = userRepository.findById(id);
        if(!userOptional.isPresent()) {
            throw new UserNotFoundException("id-"+id);
        }

        User user = userOptional.get();

        post.setUser(user);

        postRepository.save(post);
        //CREATED
        // /user/{id}  savedUser.getId
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(post.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
