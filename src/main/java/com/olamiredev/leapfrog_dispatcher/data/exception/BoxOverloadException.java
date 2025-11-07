package com.olamiredev.leapfrog_dispatcher.data.exception;

import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
public class BoxOverloadException extends RuntimeException {

    private BigDecimal boxWeight;

    private BigDecimal loadWeight;

    public String getMessage(){
        return String.format("Total weight of items %s is greater than box max weight %s", loadWeight, boxWeight);
    }

}
