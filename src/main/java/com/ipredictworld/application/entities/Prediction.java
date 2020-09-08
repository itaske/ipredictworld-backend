package com.ipredictworld.application.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ipredictworld.application.converter.ScoreConverter;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Prediction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "fixture_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Fixture fixture;

    @JsonDeserialize(using = ScoreConverter.ScoreDeserializer.class)
    @JsonSerialize(using = ScoreConverter.ScoreSerializer.class)
    @Convert(converter = ScoreConverter.class)
    private Score score;



    @JoinColumn(name = " week_prediction_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private WeekPrediction weekPrediction;


    public String toString(){
        String prediction = String.format("%s %d:%d %s\n", fixture.getHome(), score.getHome(), score.getAway(), fixture.getAway());
        System.out.println(prediction);
        return prediction;
    }


}
