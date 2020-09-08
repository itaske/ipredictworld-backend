package com.ipredictworld.application.api;


import com.ipredictworld.application.entities.*;
import com.ipredictworld.application.repository.PredictionRepository;
import com.ipredictworld.application.repository.UserRepository;
import com.ipredictworld.application.repository.WeekPredictionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/weekpredictions")
public class WeekPredictionAPI {

    @Autowired
    WeekPredictionRepository weekPredictionRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PredictionRepository predictionRepository;

//    @GetMapping
//    public ResponseEntity<?> getWeekPredictions(){
//
//    }

    @RolesAllowed("ROLE_USER")
    @PostMapping("/new/{userId}")
    public ResponseEntity<?> createWeekPrediction(@PathVariable("userId") String userId, @RequestBody WeekPrediction weekPrediction){

        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()){
            User owner = userOptional.get();
            owner.addPrediction(weekPrediction);

            List<Prediction> predictionsFromWeek = weekPrediction.getPredictions();
            weekPrediction.setPredictions(null);

            WeekPrediction savedWeekPrediction = weekPredictionRepository.save(weekPrediction);

            savedWeekPrediction.setPredictions(predictionsFromWeek.stream().map(prediction -> {
                prediction.setWeekPrediction(savedWeekPrediction);
                Prediction savedPrediction = predictionRepository.save(prediction);
                return savedPrediction;
            }).collect(Collectors.toList()));


              weekPredictionRepository.saveAndFlush(savedWeekPrediction);
            return new ResponseEntity<>(savedWeekPrediction.getPredictions(), HttpStatus.CREATED);
        } else{
            return ResponseEntity.notFound().build();
        }

    }

    @RolesAllowed("ROLE_USER")
    @PostMapping("/edit/{userId}")
    public ResponseEntity<?> editWeekPrediction(@RequestBody WeekPrediction weekPrediction, @PathVariable("userId") String userId){
        System.out.println(weekPrediction);

        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            weekPrediction.getPredictions().stream().forEach(prediction -> {
                prediction.setWeekPrediction(weekPrediction);
                predictionRepository.saveAndFlush(prediction);
            });
           return ResponseEntity.ok().build();
        }
        else return ResponseEntity.notFound().build();
    }



    @RolesAllowed("ROLE_USER")
    @GetMapping("/{userId}/{week}")
    public ResponseEntity<?> getPrediction(@PathVariable("userId") final String userId,
                                           @PathVariable("week") final int week){
        Optional<User> userOptional = userRepository.findById(userId);

        System.out.println(userOptional.get());
        if (userOptional.isPresent()) {
            Season season = new Season();
            season.setYearStarted(2020);
            season.setYearEnded(2021);
             WeekPrediction weekPrediction = weekPredictionRepository.findByWeekAndOwner(week, userOptional.get());

             System.out.println(weekPrediction);

             if (weekPrediction != null) {
                 System.out.println(weekPrediction);
                 return ResponseEntity.ok(weekPrediction);
             }else
                 return ResponseEntity.noContent().build();
        } else{
            return ResponseEntity.notFound().build();
        }
    }
}
