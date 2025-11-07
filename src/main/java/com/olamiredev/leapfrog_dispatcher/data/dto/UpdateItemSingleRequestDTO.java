package com.olamiredev.leapfrog_dispatcher.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpdateItemSingleRequestDTO {

    private Long itemId;

    private int quantityChange;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof UpdateItemSingleRequestDTO that)) return false;
        return Objects.equals(itemId, that.itemId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(itemId);
    }

}
