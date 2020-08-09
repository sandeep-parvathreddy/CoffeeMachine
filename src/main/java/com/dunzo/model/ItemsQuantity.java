package com.dunzo.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

/**
 * Created by sandeepreddy on 07/08/20.
 */
@Data
@AllArgsConstructor
public class ItemsQuantity {
    private Map<String,Integer> quantity;
}
