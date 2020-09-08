package com.ipredictworld.application.controller;

import com.ipredictworld.application.entities.Service;
import com.ipredictworld.application.entities.User;
import com.ipredictworld.application.repository.ServiceRepository;
import com.ipredictworld.application.response.ServiceResponse;
import com.ipredictworld.application.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.function.Function;


@Controller
public class ServiceController extends GenericController<Service, Service, ServiceRepository>{

    @Autowired
    ServiceRepository serviceRepository;

    Function<Service, Service> responseFunction =(service -> new Service(service));

    public ServiceController(){
        this.setTypeParameterClass(Service.class);
        this.setConversionFunction(responseFunction);
        this.setJpaRepository(serviceRepository);
    }
}
