package com.dunzo.mapper;

import com.dunzo.model.CoffeeMachineRequest;
import com.dunzo.utils.UtilService;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * Created by sandeepreddy on 07/08/20.
 */
public class CoffeeMachineRequestMapper {

    public static CoffeeMachineRequest mapFromJson(String input) {
        try {
            return (CoffeeMachineRequest) UtilService.serialize(CoffeeMachineRequest.class,input);
        } catch (JsonProcessingException e) {
            System.out.println("Exception in mapping the input to object "+e);
            return null;
        }
    }
}
