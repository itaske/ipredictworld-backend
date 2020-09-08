package com.ipredictworld.application.handle_error;

import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;

public class ErrorHandler {

    public static Map<String, Object> outputValidationMessage(BindingResult bindingResult){
        Map<String, Object> errors = new HashMap<>();
        errors.put("status","error");
        Map<String, Object> specificErrors = new HashMap<>();
        bindingResult.getFieldErrors().stream().forEach(e -> specificErrors.put(e.getField(), e.getRejectedValue()+" "+e.getDefaultMessage()));
        errors.put("errors", specificErrors);

        return errors;
    }
    public static Map<String, Object> outputExceptionMessage(Exception exception){
        Map<String, Object> errors = new HashMap<>();
        errors.put("status","error");
        errors.put("message", exception.getMessage() +" and this"+exception.getLocalizedMessage());
        return errors;
    }
}
