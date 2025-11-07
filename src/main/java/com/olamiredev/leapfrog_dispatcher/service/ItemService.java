package com.olamiredev.leapfrog_dispatcher.service;

import com.olamiredev.leapfrog_dispatcher.data.dto.ItemResourceDTO;
import com.olamiredev.leapfrog_dispatcher.data.entity.ItemEntity;
import com.olamiredev.leapfrog_dispatcher.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public List<ItemResourceDTO> findAll() {
        return itemRepository.findAll().parallelStream().map(ItemService::getItemResourceFromEntity).toList();
    }

    public static ItemResourceDTO getItemResourceFromEntity(ItemEntity itemEntity) {
        return ItemResourceDTO.builder()
                .id(itemEntity.getId())
                .name(itemEntity.getName())
                .code(itemEntity.getCode())
                .weight(itemEntity.getWeight())
                .build();
    }


}
