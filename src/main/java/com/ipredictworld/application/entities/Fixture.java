package com.ipredictworld.application.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ipredictworld.application.converter.ScoreConverter;
import com.ipredictworld.application.converter.SeasonConverter;
import com.ipredictworld.application.response.Response;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Data
public class Fixture implements Serializable, Response, Comparable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String home, away;

    private boolean played;

    @JsonDeserialize(using = ScoreConverter.ScoreDeserializer.class)
    @JsonSerialize(using = ScoreConverter.ScoreSerializer.class)
    @Convert(converter = ScoreConverter.class)
    private Score score;


    private LocalDateTime timeToBePlayed;

    private int week;

    @JsonSerialize(using = SeasonConverter.Serialize.class)
    @JsonDeserialize(using = SeasonConverter.Deserialize.class)
    @Convert(converter = SeasonConverter.class)
    private Season season;

    @Enumerated(EnumType.STRING)
    private Club.LeagueType leagueType;

    @OneToMany(mappedBy = "fixture")
    @JsonIgnore
    private List<Prediction> fixturePredictions;

    @PrePersist
    public void init(){
        played = false;
        fixturePredictions = new ArrayList<>();
    }

    public void add(Prediction prediction){
        this.fixturePredictions.add(prediction);
        prediction.setFixture(this);
    }

    public void remove(Prediction prediction){
        this.fixturePredictions.remove(prediction);
        prediction.setFixture(null);
    }

    @Override
    public int compareTo(Object o) {
        Fixture otherFixture = (Fixture)o;
        if (this.week == otherFixture.getWeek()
                && this.home.equalsIgnoreCase(otherFixture.getHome())
        && this.away.equalsIgnoreCase(otherFixture.getAway())
        && this.season.compareTo(otherFixture.getSeason())==0){
            return 0;
        } else if (this.week > otherFixture.getWeek() || season.compareTo(otherFixture.getSeason()) > 0){
            return 1;
        }else return -1;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fixture fixture = (Fixture) o;
        return week == fixture.week &&
                home.equals(fixture.home) &&
                away.equals(fixture.away) &&
                season.equals(fixture.season) &&
                leagueType == fixture.leagueType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(home, away, week, season, leagueType);
    }
}