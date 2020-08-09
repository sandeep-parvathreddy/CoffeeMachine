package com.dunzo.service;

import com.dunzo.model.Beverage;
import com.dunzo.model.BeverageComposition;
import com.dunzo.model.BeverageResponse;
import com.dunzo.model.ItemsQuantity;

/**
 * Created by sandeepreddy on 07/08/20.
 */
public interface BeverageProcessor {

    BeverageResponse process(ItemsQuantity items, Beverage beverage, BeverageComposition bevarageComposition);
}
