package com.ipredictworld.application.repository;

import com.ipredictworld.application.entities.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<Service, Long> {
}
