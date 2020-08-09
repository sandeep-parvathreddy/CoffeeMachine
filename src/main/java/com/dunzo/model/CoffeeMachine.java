package com.dunzo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

/**
 * Created by sandeepreddy on 07/08/20.
 */
@Data
public class CoffeeMachine {

    private Outlets outlets;

    @JsonProperty("total_items_quantity")
    private Map<String,Integer> totalItemsQuantity;

    private Map<String,Map<String,Integer>> beverages;
}
