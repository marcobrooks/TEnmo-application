package com.techelevator.tenmo.exceptions;

public class InsufficientFundsException extends Exception {

    public InsufficientFundsException (){
        super("Not enough funds");
    }
}
