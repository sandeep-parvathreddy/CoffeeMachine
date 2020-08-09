package com.dunzo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created by sandeepreddy on 07/08/20.
 */
@Data
public class Outlets {

    @JsonProperty("count_n")
    private int count;


}
