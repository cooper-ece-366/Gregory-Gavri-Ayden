// Written By Gregory Presser
package edu.cooper.ece366.Exceptions;

public class InvalidTripException extends RuntimeException {
    public InvalidTripException () {
        this("Invalid Trip"); 
    }
    public InvalidTripException(String message) {
        super(message);
    }
}
