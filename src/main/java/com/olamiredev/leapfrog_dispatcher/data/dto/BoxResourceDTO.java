package com.olamiredev.leapfrog_dispatcher.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class BoxResourceDTO {

    private String txRef;

    private BigDecimal totalWeight;

    private int batteryLevel;

}
