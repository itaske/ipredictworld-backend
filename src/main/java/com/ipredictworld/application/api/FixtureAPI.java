package com.ipredictworld.application.api;


import com.ipredictworld.application.controller.FixtureController;
import com.ipredictworld.application.entities.*;
import com.ipredictworld.application.repository.FixtureRepository;
import com.ipredictworld.application.repository.TicketRepository;
import com.ipredictworld.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/fixtures")
public class FixtureAPI {

    @Autowired
    FixtureController fixtureController;

    @Autowired
    FixtureRepository fixtureRepository;

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/{userId}/{seasonStart}/{seasonEnd}/{week}/{leagueType}/{competitionType}")
    public ResponseEntity<?> getAllFixturesForWeekAndSeason(@PathVariable("userId") String userId,
                                                            @PathVariable("week") int week,
                                                            @PathVariable("seasonStart") int seasonStart,
                                                            @PathVariable("seasonEnd") int seasonEnd,
                                                            @PathVariable("leagueType")Club.LeagueType leagueType,
                                                            @PathVariable("competitionType")Club.CompetitionType competitionType){
        Season season = new Season();
        season.setYearStarted(seasonStart);
        season.setYearEnded(seasonEnd);

        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            List<Ticket> tickets = ticketRepository.findByUserAndPaid(userOptional.get(), true);

            if (tickets.stream().anyMatch(ticket->{
                 return ticket.getService().getCompetitionType().compareTo(competitionType)==0 &&
                    ticket.getService().getLeagueType().compareTo(leagueType) == 0 &&
                    ticket.getService().getSeason().compareTo(season) == 0;})
            ){
               System.out.println("Entered here");
            return ResponseEntity.ok(fixtureRepository.findByWeekAndSeasonAndLeagueType(week, season, leagueType));
        }else{
                return ResponseEntity.notFound().build();
            }
    }else
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> createFixture(@RequestBody Fixture fixture){
        return fixtureController.createObject(fixture);
    }


}
