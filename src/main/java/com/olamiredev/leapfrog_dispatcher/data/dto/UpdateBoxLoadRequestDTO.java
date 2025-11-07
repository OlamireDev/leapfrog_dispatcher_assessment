package com.olamiredev.leapfrog_dispatcher.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.olamiredev.leapfrog_dispatcher.data.exception.ValidationException;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpdateBoxLoadRequestDTO {

    @NotNull
    Set<UpdateItemSingleRequestDTO> itemAdjustedQuantities;

    @JsonIgnore
    public Set<Long> getItemIds() {
        return itemAdjustedQuantities.parallelStream().map(UpdateItemSingleRequestDTO::getItemId).collect(Collectors.toSet());
    }

    public void validateRequest(){
        if(itemAdjustedQuantities.contains(null)) {
            throw new ValidationException("itemAdjustedQuantities contains null value(s)");
        }
    }

}
