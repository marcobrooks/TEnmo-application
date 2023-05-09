package com.techelevator.tenmo.exceptions;

public class InvalidReceiverException extends Exception {

    public InvalidReceiverException () {
        super("You cannot send money to yourself");
    }

}
