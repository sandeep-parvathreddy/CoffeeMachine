package com.dunzo.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

/**
 * Created by sandeepreddy on 07/08/20.
 */
public class UtilService {

    public static Object serialize(Class T, String input) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readerFor(T).readValue(input);
    }
}
