package edu.cooper.ece366.Exceptions;

public class IllegalTokenException extends Exception {
    public IllegalTokenException () {
        this("Illegal Token"); 
    }
    public IllegalTokenException(String message) {
        super(message);
    }
}
