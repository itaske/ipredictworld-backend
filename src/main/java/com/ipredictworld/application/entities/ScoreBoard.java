package com.ipredictworld.application.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ipredictworld.application.converter.SeasonConverter;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.io.Serializable;

@Entity
@Data
public class ScoreBoard implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid", strategy = "uuid")
    private String id;

    @Min(0)
    private int initialScore;

    @Min(0)
    private int currentScore;

    private int numberOfPerfectScore;

    private int numberOfWins;

    private int numberOfLosses;

    @Enumerated(EnumType.STRING)
    private Club.LeagueType leagueType;

    @Enumerated(EnumType.STRING)
    private Club.CompetitionType competitionType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userId")
    private User user;

    @Convert(converter = SeasonConverter.class)
    @JsonSerialize(using = SeasonConverter.Serialize.class)
    @JsonDeserialize(using = SeasonConverter.Deserialize.class)
    private Season season;



}
