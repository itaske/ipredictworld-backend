package com.ipredictworld.application.repository;

import com.ipredictworld.application.entities.ScoreBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScoreBoardRepository extends JpaRepository<ScoreBoard, Long> {
}
