package com.ipredictworld.application.repository;


import com.ipredictworld.application.entities.Ticket;
import com.ipredictworld.application.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, String> {

//    @Query("select t Ticket t where exists (Select u User u where  u.id = t.user.id and t.paid = true")
//    List<Ticket> findTicketsUserPaidFor(String userId);

    List<Ticket> findByUserAndPaid(User user, boolean paid);

}
