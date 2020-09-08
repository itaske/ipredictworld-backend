package com.ipredictworld.application.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ipredictworld.application.response.Response;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Ticket implements Serializable, Response {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid", strategy = "uuid")
    private String id;

    private int amountPaid;

    private LocalDateTime dateCreated;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "serviceId")
    private Service service;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    @JsonIgnore
    private User user;

    private int week;

    private LocalDateTime timePaid;

    private boolean paid;

    @PrePersist
    public void init(){
        dateCreated = LocalDateTime.now();
        paid = true;
    }

    Ticket(Ticket ticket){
        setAmountPaid(ticket.amountPaid);
        setDateCreated(ticket.getDateCreated());
        setId(ticket.getId());
        setService(ticket.getService());
        setTimePaid(ticket.getTimePaid());
        setUser(ticket.getUser());
        setWeek(ticket.getWeek());
        setPaid(ticket.isPaid());
    }

}
