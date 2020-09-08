package com.ipredictworld.application.controller;

import com.ipredictworld.application.entities.Role;
import com.ipredictworld.application.entities.User;
import com.ipredictworld.application.repository.RoleRepository;
import com.ipredictworld.application.repository.UserRepository;
import com.ipredictworld.application.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
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
    RoleRepository roleRepository;

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

        Optional<Role> roleOptional = roleRepository.findByName("ROLE_USER");
        if (roleOptional.isPresent()){
            List<Role> roles = new LinkedList<>();
            roles.add(roleOptional.get());
            object.setRoles(roles);
        }
        return super.createObject(object);
    }
}
