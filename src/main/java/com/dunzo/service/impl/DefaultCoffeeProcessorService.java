package com.dunzo.service.impl;

import com.dunzo.model.*;
import com.dunzo.service.BeverageProcessor;
import com.dunzo.service.CoffeeProcessor;
import com.dunzo.exception.CoffeeMachineRequestProcessingException;
import com.dunzo.exception.CoffeeMachineRequestValidationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by sandeepreddy on 07/08/20.
 */
public class DefaultCoffeeProcessorService implements CoffeeProcessor {

    private BeverageProcessor beverageProcessor;

    public DefaultCoffeeProcessorService() {
        beverageProcessor = new DefaultBeverageProcessor();
    }

    /*
    * Validates the request
    * Constructs a executorService with ThreadPoolSize based on outlets count
    * Builds request callable for all the beverages to be prepared
    * invokes all callables
    * constructs output
    *
    * **/
    public CoffeeMachineResponse process(CoffeeMachineRequest coffeeMachineRequest) throws CoffeeMachineRequestValidationException, CoffeeMachineRequestProcessingException {
        if(!validateRequest(coffeeMachineRequest)){
            throw new CoffeeMachineRequestValidationException("Received request is invalid to process");
        }
        ExecutorService executorService = Executors.newFixedThreadPool(coffeeMachineRequest.getMachine().getOutlets().getCount());
        List<Callable<BeverageResponse>> beverageRequests = formBeverageProcessingRequests(coffeeMachineRequest);
        List<Future<BeverageResponse>> processingResponse = null;
        try {
            processingResponse = executorService.invokeAll(beverageRequests);
        } catch (InterruptedException e) {
            throw new CoffeeMachineRequestProcessingException("Exception occurred in processing the beverage's : ",e);
        } finally {
            executorService.shutdown();
        }
        return buildOutput(processingResponse);

    }

    @Override
    public void refill(Map<String, Integer> items) {
        //TODO
        // Refill the inventory with the items provided
    }

    /*
    * Constructing the required output
    *
    * */
    private CoffeeMachineResponse buildOutput(List<Future<BeverageResponse>> processingResponse) {
        CoffeeMachineResponse coffeeMachineResponse = new CoffeeMachineResponse();
        processingResponse.stream().forEach(element -> {
            try {
                coffeeMachineResponse.add(element.get().getMessage());
            } catch (Exception ex) {
                System.out.println("Exception occurred in framing the response : " +ex);
            }
        });
        return coffeeMachineResponse;
    }


    /*
    *
    * Checks if request is not null and has outlets to prepare the beverage
    *
    * */
    private boolean validateRequest(CoffeeMachineRequest coffeeMachineRequest) {
        return coffeeMachineRequest!=null && coffeeMachineRequest.getMachine().getOutlets().getCount()>0;
    }


    private List<Callable<BeverageResponse>> formBeverageProcessingRequests(CoffeeMachineRequest coffeeMachineRequest){
        List<Callable<BeverageResponse>> callableList = new ArrayList<>();
        coffeeMachineRequest.getMachine().getBeverages().forEach((key,value) ->
                callableList.add(() -> beverageProcessor.process(new ItemsQuantity(coffeeMachineRequest.getMachine().getTotalItemsQuantity()),new Beverage(key),new BeverageComposition(value))));
        return callableList;
    }


}
