package com.ipredictworld.application.response;

import com.ipredictworld.application.entities.Service;
import lombok.Data;

@Data
public class ServiceResponse implements Response{

    Service service;

    public ServiceResponse(Service service){
        this.service = service;
    }
}
