package com.ipredictworld.application.api;


import com.ipredictworld.application.entities.Ticket;
import com.ipredictworld.application.entities.User;
import com.ipredictworld.application.repository.TicketRepository;
import com.ipredictworld.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/tickets")
public class TicketAPI {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TicketRepository ticketRepository;

    @PostMapping("/{userId}")
    public ResponseEntity<?> createTicket(@PathVariable("userId") final String id, @RequestBody Ticket ticket){
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()){
            ticket.setUser(userOptional.get());
            Ticket savedTicket = ticketRepository.save(ticket);
            return new ResponseEntity<>(savedTicket, HttpStatus.CREATED);
        } else
            return ResponseEntity.notFound().build();
    }

    @GetMapping("/paid/{userId}")
    public ResponseEntity<?> checkTicketsAvailableForUser(@PathVariable("userId") final String id){
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            return ResponseEntity.ok(ticketRepository.findByUserAndPaid(userOptional.get(), true).size()>0);
        } else
            return ResponseEntity.ok(false);
        }

}
