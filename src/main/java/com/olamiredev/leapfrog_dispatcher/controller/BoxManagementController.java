package com.olamiredev.leapfrog_dispatcher.controller;

import com.olamiredev.leapfrog_dispatcher.data.dto.*;
import com.olamiredev.leapfrog_dispatcher.service.BoxManagementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/box")
@RequiredArgsConstructor
public class BoxManagementController {

    private final BoxManagementService boxManagementService;

    @PostMapping()
    public ResponseEntity<LeapFrogApiResponse<BoxResourceDTO>> createBox(@Valid @RequestBody CreateBoxRequestDTO requestDTO) {
        return new ResponseEntity<>(LeapFrogApiResponse
                        .successfulApiResponse(boxManagementService.createBox(requestDTO)), HttpStatusCode.valueOf(201));
    }

    @PutMapping("/{box_txRef}/load")
    public ResponseEntity<LeapFrogApiResponse<LoadedBoxItemsResponseDTO>> updateBoxLoad(@Valid @RequestBody UpdateBoxLoadRequestDTO requestDTO,
                                                                                        @PathVariable String box_txRef) {
        return ResponseEntity
                .ok(LeapFrogApiResponse
                        .successfulApiResponse(boxManagementService.updateBoxLoad(box_txRef, requestDTO)));
    }

    @GetMapping("/{box_txRef}/loaded_items")
    public ResponseEntity<LeapFrogApiResponse<LoadedBoxItemsResponseDTO>> getLoadedBoxItems(@PathVariable String box_txRef) {
        return ResponseEntity
                .ok(LeapFrogApiResponse
                        .successfulApiResponse(boxManagementService.getBoxLoad(box_txRef)));
    }

    @GetMapping("/available")
    public ResponseEntity<LeapFrogApiResponse<List<BoxResourceDTO>>>  getAvailableBoxesForLoading() {
        return ResponseEntity
                .ok(LeapFrogApiResponse
                        .successfulApiResponse(boxManagementService.getLoadableBoxes()));
    }

    @GetMapping("/{box_txRef}/battery_level")
    public ResponseEntity<LeapFrogApiResponse<BoxBatteryLevelResponseDTO>> getBatteryLevel(@PathVariable String box_txRef) {
        return ResponseEntity
                .ok(LeapFrogApiResponse
                        .successfulApiResponse(boxManagementService.getBoxBatteryLevel(box_txRef)));
    }


}
