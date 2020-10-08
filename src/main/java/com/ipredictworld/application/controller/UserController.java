package com.ipredictworld.application.controller;

import com.ipredictworld.application.entities.User;
import com.ipredictworld.application.repository.UserRepository;
import com.ipredictworld.application.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class UserController extends GenericController<User, UserResponse, UserRepository>{

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    Function<User, UserResponse> responseFunction =(user -> new UserResponse(user));

    public UserController(){
        this.setTypeParameterClass(User.class);
        this.setConversionFunction(responseFunction);
        this.setJpaRepository(userRepository);
    }

    public UserController(UserRepository userRepository, Function<User, UserResponse> response, Class<User> type) {

        super(userRepository,response, type);

    }

    @Override
    public ResponseEntity<UserResponse> createObject(User object) {
        object.setPassword(passwordEncoder.encode(object.getPassword()));
        object.setRole(User.Role.ROLE_USER);
        return super.createObject(object);
    }
}
