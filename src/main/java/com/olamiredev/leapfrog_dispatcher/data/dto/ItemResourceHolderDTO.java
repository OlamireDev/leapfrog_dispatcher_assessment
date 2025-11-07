package com.olamiredev.leapfrog_dispatcher.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ItemResourceHolderDTO {

    private ItemResourceDTO item;

    private int quantity;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ItemResourceHolderDTO that = (ItemResourceHolderDTO) o;
        return Objects.equals(item.getCode(), that.item.getCode());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(item);
    }
}
