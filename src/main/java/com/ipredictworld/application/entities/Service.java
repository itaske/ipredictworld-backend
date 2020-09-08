package com.ipredictworld.application.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ipredictworld.application.converter.SeasonConverter;
import com.ipredictworld.application.response.Response;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@NoArgsConstructor
public class Service implements Serializable, Response {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int price;

    @Enumerated(EnumType.STRING)
    private Club.CompetitionType competitionType;

    @Enumerated(EnumType.STRING)
    private Club.LeagueType leagueType;


    @JsonSerialize(using = SeasonConverter.Serialize.class)
    @JsonDeserialize(using = SeasonConverter.Deserialize.class)
    @Convert(converter = SeasonConverter.class)
    private Season season;

    public Service (Service service){
        this.setSeason(service.getSeason());
        this.setPrice(service.getPrice());
        this.setLeagueType(service.getLeagueType());
        this.setCompetitionType(service.getCompetitionType());
        this.setId(service.getId());
    }



}
