package com.dunzo.exception;

/**
 * Created by sandeepreddy on 07/08/20.
 */
public class CoffeeMachineRequestProcessingException extends Exception{

    public CoffeeMachineRequestProcessingException(String message,Throwable cause) {
        super(message,cause);
    }
}
