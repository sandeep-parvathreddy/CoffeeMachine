package com.dunzo.model;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sandeepreddy on 07/08/20.
 */
@Data
public class CoffeeMachineResponse {

    private List<String> response;

    public CoffeeMachineResponse() {
        response = new ArrayList<>();
    }

    public void add(String message){
        response.add(message);
    }



}
