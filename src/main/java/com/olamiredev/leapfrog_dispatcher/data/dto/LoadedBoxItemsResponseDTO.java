package com.olamiredev.leapfrog_dispatcher.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class LoadedBoxItemsResponseDTO {

    private String boxTxRef;

    private BigDecimal weightAvailable;

    private List<ItemResourceHolderDTO> loadedItems;

}
