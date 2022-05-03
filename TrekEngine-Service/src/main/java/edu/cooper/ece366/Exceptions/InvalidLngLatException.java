package edu.cooper.ece366.Exceptions;

public class InvalidLngLatException extends RuntimeException {
    public InvalidLngLatException () {
        this("Invalid LngLat"); 
    }
    public InvalidLngLatException(String message) {
        super(message);
    }    
}
