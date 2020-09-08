package com.ipredictworld.application.repository;

import com.ipredictworld.application.entities.Club;
import com.ipredictworld.application.entities.Fixture;
import com.ipredictworld.application.entities.Season;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FixtureRepository extends JpaRepository<Fixture, Long> {

    List<Fixture> findByWeekAndSeasonAndLeagueType(int week, Season season, Club.LeagueType leagueType);
}
