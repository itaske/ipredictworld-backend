package com.ipredictworld.application.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ipredictworld.application.converter.SeasonConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(exclude = {"predictions", "owner"})
public class WeekPrediction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    @JsonIgnore
    private User owner;

    @OneToMany(mappedBy = "weekPrediction")
    private List<Prediction> predictions= new ArrayList<>();

    private int week;

    @Enumerated(EnumType.STRING)
    private Club.LeagueType leagueType;

    @Enumerated(EnumType.STRING)
    private Club.CompetitionType competitionType;

    @JsonSerialize(using = SeasonConverter.Serialize.class)
    @JsonDeserialize(using = SeasonConverter.Deserialize.class)
    @Convert(converter = SeasonConverter.class)
    private Season season;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm", shape= JsonFormat.Shape.STRING)
    private LocalDateTime timePredicted;


   @PrePersist
    public void init(){
        timePredicted = LocalDateTime.now();
    }

    public String toString(){
       StringBuilder stringBuilder = new StringBuilder();
       predictions.stream().forEach(prediction ->{
           stringBuilder.append(prediction.toString());
       });
       return stringBuilder.toString();
    }
}
