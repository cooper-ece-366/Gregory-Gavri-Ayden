// Written By Gregory Presser
package edu.cooper.ece366.Exceptions;

public class InvalidTokenException extends Exception {
    public InvalidTokenException () {
        this("Invalid Token"); 
    }
    public InvalidTokenException(String message) {
        super(message);
    }
}
