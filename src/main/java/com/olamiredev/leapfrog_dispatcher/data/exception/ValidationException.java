package com.olamiredev.leapfrog_dispatcher.data.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ValidationException extends RuntimeException{

    private String message;

}
