package com.olamiredev.leapfrog_dispatcher.service;

import com.olamiredev.leapfrog_dispatcher.data.dto.*;
import com.olamiredev.leapfrog_dispatcher.data.entity.BoxEntity;
import com.olamiredev.leapfrog_dispatcher.data.entity.BoxLoadedItemEntity;
import com.olamiredev.leapfrog_dispatcher.data.entity.ItemEntity;
import com.olamiredev.leapfrog_dispatcher.data.exception.BoxAlreadyExistsException;
import com.olamiredev.leapfrog_dispatcher.data.exception.BoxNotFoundException;
import com.olamiredev.leapfrog_dispatcher.data.exception.BoxOverloadException;
import com.olamiredev.leapfrog_dispatcher.repository.BoxLoadedItemRepository;
import com.olamiredev.leapfrog_dispatcher.repository.BoxRepository;
import com.olamiredev.leapfrog_dispatcher.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static com.olamiredev.leapfrog_dispatcher.service.ItemService.getItemResourceFromEntity;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoxManagementService {

    private final BoxRepository boxRepository;
    private final ItemRepository itemRepository;
    private final BoxLoadedItemRepository boxLoadedItemRepository;

    @Transactional
    public BoxResourceDTO createBox(CreateBoxRequestDTO requestDTO) {
        if(boxRepository.findById(requestDTO.getTxRef()).isPresent()) {
            throw new BoxAlreadyExistsException(requestDTO.getTxRef());
        }
        var boxEntity = BoxEntity.builder()
                            .txRef(requestDTO.getTxRef())
                            .weight(requestDTO.getWeight())
                            .batteryLevel(requestDTO.getBatteryLevel())
                            .state(requestDTO.getState())
                            .loadedItemEntities(List.of())
                            .build();
        boxEntity = boxRepository.save(boxEntity);
        return getBoxResourceFromEntity(boxEntity);
    }

    @Transactional
    public LoadedBoxItemsResponseDTO updateBoxLoad(String boxTxRef, UpdateBoxLoadRequestDTO requestDTO) {
        requestDTO.validateRequest();
        var boxOpt = boxRepository.findAvailableBoxByTxRef(boxTxRef);
        if(boxOpt.isEmpty()) {
            throw new BoxNotFoundException(boxTxRef);
        }
        var box = boxOpt.get();
        var currentLoadedItemsMap = box.getLoadedItemEntities().parallelStream().collect(Collectors.toMap(k -> k.getItem().getId(), v -> v));
        var boxLoadedItemToDelete = new ArrayList<BoxLoadedItemEntity>();
        var itemsToLoad = itemRepository.findAllById(requestDTO
                .getItemIds())
                .parallelStream()
                .collect(Collectors.toMap(ItemEntity::getId, v -> v));
        requestDTO.getItemAdjustedQuantities().parallelStream().forEach(singleRequestDTO -> {
            var itemId = singleRequestDTO.getItemId();
            var adjustedQuantity = singleRequestDTO.getQuantityChange();
            if(adjustedQuantity == 0) {
                return;
            }
            if(adjustedQuantity < 0){
                if(!currentLoadedItemsMap.containsKey(itemId)) {
                    log.warn("Item with id {} not loaded, skipping... ", itemId);
                    return;
                }
                currentLoadedItemsMap.get(itemId).setQuantity(currentLoadedItemsMap.get(itemId).getQuantity() - Math.abs(adjustedQuantity));
                if(currentLoadedItemsMap.get(itemId).getQuantity() <= 0) {
                    var boxLoadedItem = currentLoadedItemsMap.get(itemId);
                    box.getLoadedItemEntities().remove(boxLoadedItem);
                    currentLoadedItemsMap.remove(itemId);
                    boxLoadedItemToDelete.add(boxLoadedItem);
                }
            } else {
                if (!itemsToLoad.containsKey(itemId)) {
                    log.warn("Item with id {} not found in database, skipping... ", itemId);
                    return;
                }
                if (currentLoadedItemsMap.containsKey(itemId)) {
                    currentLoadedItemsMap.get(itemId).setQuantity(currentLoadedItemsMap.get(itemId).getQuantity() + adjustedQuantity);
                } else {
                    var boxLoadedItem = BoxLoadedItemEntity.builder().item(itemsToLoad.get(itemId)).box(box).quantity(adjustedQuantity).build();
                    boxLoadedItem = boxLoadedItemRepository.save(boxLoadedItem);
                    box.getLoadedItemEntities().add(boxLoadedItem);
                    currentLoadedItemsMap.put(itemId, boxLoadedItem);
                }
            }
        });
        var loadWeight = getTotalLoadWeight(box);
        if(loadWeight.compareTo(box.getWeight()) > 0) {
            throw new BoxOverloadException(box.getWeight(), loadWeight);
        }
        if(!boxLoadedItemToDelete.isEmpty()) {
            boxLoadedItemRepository.deleteAll(boxLoadedItemToDelete);
        }
        boxLoadedItemRepository.saveAll(currentLoadedItemsMap.values());
        return getLoadedBoxItemsResponse(box);
    }

    public LoadedBoxItemsResponseDTO getBoxLoad(String boxTxRef) {
        var boxOpt = boxRepository.findById(boxTxRef);
        if(boxOpt.isEmpty()) {
            throw new BoxNotFoundException(boxTxRef);
        }
        return getLoadedBoxItemsResponse(boxOpt.get());
    }

    public List<BoxResourceDTO> getLoadableBoxes() {
        var loadableBox = boxRepository.findAllLoadableBoxes();
        return loadableBox.parallelStream().map(this::getBoxResourceFromEntity).toList();
    }

    public BoxBatteryLevelResponseDTO getBoxBatteryLevel(String boxTxRef) {
        var boxOpt = boxRepository.findById(boxTxRef);
        if(boxOpt.isEmpty()) {
            throw new BoxNotFoundException(boxTxRef);
        }
        return new BoxBatteryLevelResponseDTO(boxOpt.get().getBatteryLevel());
    }

    public BoxResourceDTO getBoxResourceFromEntity(BoxEntity boxEntity) {
        return BoxResourceDTO.builder()
                    .txRef(boxEntity.getTxRef())
                    .totalWeight(boxEntity.getWeight())
                    .batteryLevel(boxEntity.getBatteryLevel())
                    .build();
    }

    public LoadedBoxItemsResponseDTO getLoadedBoxItemsResponse(BoxEntity boxEntity) {
        var loadedWeight = getTotalLoadWeight(boxEntity);
        return LoadedBoxItemsResponseDTO.builder()
                .boxTxRef(boxEntity.getTxRef())
                .loadedItems(boxEntity.getLoadedItemEntities().stream().map(this::getBoxItemResourceFromEntity).toList())
                .weightAvailable(boxEntity.getWeight().subtract(loadedWeight))
                .build();
    }

    public ItemResourceHolderDTO getBoxItemResourceFromEntity(BoxLoadedItemEntity boxLoadedItemEntity) {
        return ItemResourceHolderDTO.builder()
                .quantity(boxLoadedItemEntity.getQuantity())
                .item(getItemResourceFromEntity(boxLoadedItemEntity.getItem())).build();
    }
    public BigDecimal getTotalLoadWeight(BoxEntity boxEntity) {
        AtomicReference<BigDecimal> loadedWeight = new AtomicReference<>(BigDecimal.ZERO);
        boxEntity.getLoadedItemEntities().forEach(boxLoadedItemEntity ->
                loadedWeight.set(loadedWeight.get().add(boxLoadedItemEntity.getItem().getWeight()
                        .multiply(BigDecimal.valueOf(boxLoadedItemEntity.getQuantity()))))
        );
        return loadedWeight.get();
    }

}
