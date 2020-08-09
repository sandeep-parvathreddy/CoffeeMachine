package com.dunzo.service.impl;

import com.dunzo.model.*;
import com.dunzo.service.BeverageProcessor;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by sandeepreddy on 07/08/20.
 */

public class DefaultBeverageProcessor implements BeverageProcessor {

    /*
    * Validates if all items are present for the beverage to prepare
    * Also checks if all the items have enough quantity to prepare the beverage
    * Consumes the items required foe beverage
    *
    * **/
    @Override
    public synchronized BeverageResponse process(ItemsQuantity items, Beverage beverage, BeverageComposition beverageComposition)  {

        List<String> missingItems = getMissingItems(items, beverageComposition);
        if(!missingItems.isEmpty()){
            return new BeverageResponse(beverage.getName() + " cannot be prepared because " +String.join(",",missingItems) + " is not available");
        }
        else {
            for (String item : beverageComposition.getQuantity().keySet()) {
                if (items.getQuantity().get(item) < beverageComposition.getQuantity().get(item)) {
                    return new BeverageResponse(beverage.getName() + " cannot be prepared because item " + item + " is " + items.getQuantity().get(item));
                }
            }
            consumeItemsForTheBeverage(items, beverageComposition);
            return new BeverageResponse(beverage.getName() + " is prepared");
        }

    }

    private List<String> getMissingItems(ItemsQuantity items, BeverageComposition beverageComposition) {
        return beverageComposition.getQuantity().keySet().stream().filter(key -> !items.getQuantity().containsKey(key)).
                                                                   distinct().collect(Collectors.toList());
    }

    private void consumeItemsForTheBeverage(ItemsQuantity items, BeverageComposition beverageComposition) {
        beverageComposition.getQuantity().forEach((k, v) -> items.getQuantity().put(k, items.getQuantity().get(k) - v));
    }

}
