// Written By Gregory Presser
package edu.cooper.ece366.Exceptions;

public class IllegalJsonException extends RuntimeException {
    public IllegalJsonException () {
        this("Illegal Json"); 
    }
    public IllegalJsonException(String message) {
        super(message);
    }
}
