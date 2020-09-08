package com.ipredictworld.application.entities;


import com.fasterxml.jackson.annotation.*;
import javafx.beans.property.ObjectProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(exclude = {"roles", "weekPredictions"})
@Table(name="ipredict_user")
public class User implements Serializable {

    public enum Gender{
        MALE,
        FEMALE
    };

    public enum AccountState{
        NEW,
        ACTIVE,
        INACTIVE
    }


    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid", strategy = "uuid")
    private String id;

    @Email
    @NotBlank
    @NotNull
    @Column(unique = true)
    @NaturalId
    private String email;

    @NotNull
    @NotBlank
    private String password;

    private String name;

    private String country;

    @Column(unique =true)
    private String username;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private boolean nonLocked;

    @Enumerated(EnumType.STRING)
    private AccountState accountState;

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private LocalDate birthDate;

    private String phoneNumber;

    @PrePersist
    public void init (){
        nonLocked = true;
        accountState = AccountState.NEW;
        gender = Gender.MALE;
        country="Nigeria";
    }

    @ManyToMany
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name="user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="role_id", referencedColumnName = "id"))
    private List<Role> roles = new ArrayList<>();



    @OneToMany(mappedBy = "owner",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<WeekPrediction> weekPredictions=new ArrayList<>();

    public String toString(){
        return String.format("[id:%s, name:%s]", id, name);
    }


    public void addPrediction(WeekPrediction weekPrediction){
        this.weekPredictions.add(weekPrediction);
        weekPrediction.setOwner(this);
    }

    public void removePrediction(WeekPrediction weekPrediction){
        this.weekPredictions.remove(weekPrediction);
        weekPrediction.setOwner(null);
    }

}