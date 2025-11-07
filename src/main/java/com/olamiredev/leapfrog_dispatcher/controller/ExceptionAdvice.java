package com.olamiredev.leapfrog_dispatcher.controller;

import com.olamiredev.leapfrog_dispatcher.data.dto.LeapFrogApiResponse;
import com.olamiredev.leapfrog_dispatcher.data.dto.LeapFrogErrorResponse;
import com.olamiredev.leapfrog_dispatcher.data.exception.BoxNotFoundException;
import com.olamiredev.leapfrog_dispatcher.data.exception.BoxOverloadException;
import com.olamiredev.leapfrog_dispatcher.data.exception.ValidationException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(exception =  Exception.class)
    public ResponseEntity<LeapFrogApiResponse<LeapFrogErrorResponse>> handleException(Exception e) {
        if(e instanceof BoxNotFoundException boxNotFoundException){
            return new ResponseEntity<>(LeapFrogApiResponse
                    .failedApiResponse(new LeapFrogErrorResponse(boxNotFoundException.getMessage())),
                    HttpStatusCode.valueOf(404));
        }
        if(e instanceof BoxOverloadException bOE){
            return new ResponseEntity<>(LeapFrogApiResponse
                    .failedApiResponse(new LeapFrogErrorResponse(bOE.getMessage())),
                    HttpStatusCode.valueOf(400));
        }
        if(e instanceof ValidationException vE){
            return new ResponseEntity<>(LeapFrogApiResponse
                    .failedApiResponse(new LeapFrogErrorResponse(vE.getMessage())),
                    HttpStatusCode.valueOf(400));
        }
        if(e instanceof MethodArgumentNotValidException methodArgumentNotValidException){
            return new ResponseEntity<>(LeapFrogApiResponse
                    .failedApiResponse(new LeapFrogErrorResponse(methodArgumentNotValidException.getMessage())),
                    HttpStatusCode.valueOf(400));
        }
        return ResponseEntity.internalServerError()
                .body(LeapFrogApiResponse.failedApiResponse(new LeapFrogErrorResponse(e.getMessage())));
    }

}
