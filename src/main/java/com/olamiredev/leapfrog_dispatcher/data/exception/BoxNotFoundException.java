package com.olamiredev.leapfrog_dispatcher.data.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BoxNotFoundException extends RuntimeException {

    private String boxTxRef;

    @Override
    public String getMessage() {
        return String.format("Box %s not found", boxTxRef);
    }

}
