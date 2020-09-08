package com.ipredictworld.application.api;

import com.ipredictworld.application.controller.ServiceController;
import com.ipredictworld.application.entities.Service;
import com.ipredictworld.application.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/services")
public class ServiceAPI {

    @Autowired
    ServiceRepository serviceRepository;

    @Autowired
    ServiceController serviceController;

    @RolesAllowed("ROLE_USER")
    @GetMapping
    public ResponseEntity<?> getServices(){
        List<Service> services = serviceRepository.findAll();
        return ResponseEntity.ok(services);
    }


    @RolesAllowed("ROLE_USER")
    @GetMapping("/competitionType/{competitionType}")
    public ResponseEntity<?> getServicesBasedOnCompetitionType(@PathVariable("competitionType")
                                                                   final String competitionType){
        List<Service> services = serviceRepository.findAll();
        List<Service> filterServices =  services.stream().
                filter(service-> service.getCompetitionType().toString().compareToIgnoreCase(competitionType)==0)
                .collect(Collectors.toList());

        return ResponseEntity.ok(filterServices);
    }

    @RolesAllowed("ROLE_USER")
    @GetMapping("/leagueType/{leagueType}")
    public ResponseEntity<?> getServicesBasedOnLeagueType(@PathVariable("leagueType")
                                                          final String leagueType){
        List<Service> services = serviceRepository.findAll();
        List<Service> filterServices =  services.stream().
                filter(service-> service.getLeagueType().toString().compareToIgnoreCase(leagueType)==0)
                .collect(Collectors.toList());

        return ResponseEntity.ok(filterServices);
    }

    @PostMapping
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<?> createService(@RequestBody Service service){
        return serviceController.createObject(service);
    }
}
