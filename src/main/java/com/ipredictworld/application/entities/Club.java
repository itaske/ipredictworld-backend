package com.ipredictworld.application.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Club {

    public enum CompetitionType{
        WEEKLY,
        FULL_SEASON
    }

    public enum LeagueType{
        CHAMPIONS_LEAGUE,
        PREMIER_LEAGUE,
        LA_LIGA
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String logoURL;

    @Enumerated(EnumType.STRING)
    private LeagueType leagueType;

}
