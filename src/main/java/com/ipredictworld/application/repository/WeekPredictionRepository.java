package com.ipredictworld.application.repository;

import com.ipredictworld.application.entities.Season;
import com.ipredictworld.application.entities.User;
import com.ipredictworld.application.entities.WeekPrediction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeekPredictionRepository extends JpaRepository<WeekPrediction, Long> {

    WeekPrediction findByWeekAndOwner(int week, User owner);
}
