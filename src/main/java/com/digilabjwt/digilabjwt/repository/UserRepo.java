package com.digilabjwt.digilabjwt.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.digilabjwt.digilabjwt.entity.User;

public interface UserRepo extends MongoRepository<User, String> {

    public Optional<User> findByEmail(String email);

}
