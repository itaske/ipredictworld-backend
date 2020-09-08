package com.ipredictworld.application.repository;

import com.ipredictworld.application.entities.Prediction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PredictionRepository extends JpaRepository<Prediction, Long> {
}
