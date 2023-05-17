package com.digilabjwt.digilabjwt.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digilabjwt.digilabjwt.entity.User;
import com.digilabjwt.digilabjwt.repository.UserRepo;

@RestController
// @CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/info")
    public User getUserDetails() {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepo.findByEmail(email).get();
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return (List<User>) userRepo.findAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id) {
        // Find the entity
        User std = userRepo.findById(id).get();
        // Then delete the entity
        userRepo.delete(std);
        return ResponseEntity.ok("Deleted string: " + id);

    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User user) {
        // check if user exists
        Optional<User> existingUser = userRepo.findById(id);
        if (!existingUser.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        // update book data
        User usr = existingUser.get();
        usr.setActive(user.getActive());
        usr.setRole(user.getRole());
        usr.setPermission(user.getPermission());

        // save updated user
        User savedUser = userRepo.save(usr);
        return ResponseEntity.ok(savedUser);
    }

}
