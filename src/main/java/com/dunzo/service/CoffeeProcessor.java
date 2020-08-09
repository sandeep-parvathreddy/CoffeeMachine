package com.dunzo.service;

import com.dunzo.model.CoffeeMachineRequest;
import com.dunzo.model.CoffeeMachineResponse;
import com.dunzo.exception.CoffeeMachineRequestProcessingException;
import com.dunzo.exception.CoffeeMachineRequestValidationException;

import java.util.Map;

/**
 * Created by sandeepreddy on 07/08/20.
 */
public interface CoffeeProcessor {

    CoffeeMachineResponse process(CoffeeMachineRequest coffeeMachineRequest) throws CoffeeMachineRequestValidationException, CoffeeMachineRequestProcessingException;

    void refill(Map<String,Integer> items);
}
