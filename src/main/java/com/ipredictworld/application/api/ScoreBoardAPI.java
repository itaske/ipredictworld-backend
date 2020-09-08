package com.ipredictworld.application.api;

import com.ipredictworld.application.entities.Club;
import com.ipredictworld.application.entities.ScoreBoard;
import com.ipredictworld.application.entities.Season;
import com.ipredictworld.application.repository.ScoreBoardRepository;
import com.ipredictworld.application.response.ScoreBoardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/scoreboards")
public class ScoreBoardAPI {

    @Autowired
    ScoreBoardRepository scoreBoardRepository;

    @RolesAllowed("ROLE_USER")
    @GetMapping("/{seasonStart}/{seasonEnd}/{leagueType}")
    public ResponseEntity<?> getScoreboardBySeason(@PathVariable("seasonStart") final int seasonStart,
                                                   @PathVariable("seasonEnd") final int seasonEnd,
                                                   @PathVariable("leagueType") final Club.LeagueType leagueType){
        Season season = new Season();
        season.setYearStarted(seasonStart);
        season.setYearEnded(seasonEnd);


        List<ScoreBoard> scoreBoards = scoreBoardRepository.findAll();
        List<ScoreBoardResponse> filteredScoreBoards = scoreBoards.stream()
                .filter(scoreBoard -> {
                    return scoreBoard.getSeason().compareTo(season) == 0 &&
                            scoreBoard.getLeagueType().compareTo(leagueType) == 0;
                }).sorted((ScoreBoard a, ScoreBoard b)->{
                    int scoreA = (a.getCurrentScore()+a.getInitialScore());
                    int scoreB = (b.getCurrentScore()+b.getInitialScore());
                    return Integer.compare(scoreA, scoreB);
        }).collect(Collectors.collectingAndThen(Collectors.toList(), list->{
            Collections.reverse(list);
            return list.stream();
                }))
                .map(ScoreBoardResponse::new)
                .collect(Collectors.toList());


        return ResponseEntity.ok(filteredScoreBoards);
    }

}
