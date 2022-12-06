package com.reto_backend.reto_backend.error;

public class DataValidationException extends RuntimeException{

    public DataValidationException(String errorMessage){
        super(errorMessage);
    }
    
}
