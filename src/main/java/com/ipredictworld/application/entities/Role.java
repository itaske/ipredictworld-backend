package com.ipredictworld.application.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
public class Role implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private List<User> userList;

    public String toString (){
        return name;
    }
}
