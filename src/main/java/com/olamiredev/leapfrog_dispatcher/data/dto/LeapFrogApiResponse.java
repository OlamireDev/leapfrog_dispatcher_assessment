package com.olamiredev.leapfrog_dispatcher.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LeapFrogApiResponse<T> {

    private String message;

    private T data;

    public static <T> LeapFrogApiResponse<T> successfulApiResponse(T data) {
        return new LeapFrogApiResponse<>("Success", data);
    }

    public static <T> LeapFrogApiResponse<T> failedApiResponse(T data) {
        return new LeapFrogApiResponse<>("Error", data);
    }

}
