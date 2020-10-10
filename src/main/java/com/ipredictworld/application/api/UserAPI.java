package com.ipredictworld.application.api;

import com.ipredictworld.application.controller.UserController;
import com.ipredictworld.application.entities.Ticket;
import com.ipredictworld.application.entities.User;
import com.ipredictworld.application.handle_error.ErrorHandler;
import com.ipredictworld.application.repository.TicketRepository;
import com.ipredictworld.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/users")
public class UserAPI {


    UserController userController;

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    UserRepository userRepository;



    public UserAPI(UserController userController){
        this.userController = userController;
    }

    @GetMapping
    @Transactional
    public ResponseEntity<?> getAll(@RequestParam(name="size", required = false, defaultValue = "25") int size,
                                    @RequestParam(name="currentPage", required = false, defaultValue = "0") int currentPage,
                                    @RequestParam(name="direction", required = false, defaultValue = "ASC") String direction,
                                    @RequestParam(name="attribute", required = false, defaultValue = "name") String attribute){
        return userController.getAll(currentPage, size, direction, attribute);
    }

    @RolesAllowed("ROLE_USER")
    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity<?> getUser(@PathVariable(name="id") final String id){
        return userController.getById(id);
    }


    @PostMapping
    @Transactional
    public ResponseEntity<?> createUser(@RequestBody  @Valid User user, BindingResult bindingResult){
        System.out.println("user"+user);
         if (bindingResult.hasErrors()){

          return new ResponseEntity(ErrorHandler.outputValidationMessage(bindingResult), HttpStatus.BAD_REQUEST);

        }

        try{
            return userController.createObject(user);

        }catch(Exception exception){
            return new ResponseEntity<>(ErrorHandler.outputExceptionMessage(exception), HttpStatus.BAD_REQUEST);
        }
    }

    @RolesAllowed("ROLE_USER")
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> editUser(@RequestBody Map<String,Object> userDetails, @PathVariable("id") final String id){
        return userController.editObject(userDetails, id);
    }

    @RolesAllowed("ROLE_CAN_DELETE_USER")
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> deleteUser(@PathVariable("id") final String id){
        return userController.deleteObjectById(id);
    }




}
