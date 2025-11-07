package com.olamiredev.leapfrog_dispatcher.data.dto;

import com.olamiredev.leapfrog_dispatcher.data.discrete.BoxDispatchState;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateBoxRequestDTO {

    @NotNull
    @NotEmpty
    @Length(max = 20)
    private String txRef;

    @DecimalMax("500.0")
    @DecimalMin("0.0")
    private BigDecimal weight;

    @Max(100)
    @Min(0)
    private int batteryLevel;

    private BoxDispatchState state;


}
