package com.ipredictworld.application.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ipredictworld.application.entities.User;
import lombok.Data;

@Data
public class UserResponse implements Response{

    @JsonIgnoreProperties({"password"})
    private User user;

    public UserResponse(User user){
        this.user = user;
    }
}
