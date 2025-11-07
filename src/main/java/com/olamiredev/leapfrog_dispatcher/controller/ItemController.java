package com.olamiredev.leapfrog_dispatcher.controller;

import com.olamiredev.leapfrog_dispatcher.data.dto.ItemResourceDTO;
import com.olamiredev.leapfrog_dispatcher.data.dto.LeapFrogApiResponse;
import com.olamiredev.leapfrog_dispatcher.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/item")
@RequiredArgsConstructor
public class ItemController {


    private final ItemService itemService;

    @GetMapping("/all")
    public ResponseEntity<LeapFrogApiResponse<List<ItemResourceDTO>>> getAllItems() {
        return ResponseEntity
                .ok(LeapFrogApiResponse
                        .successfulApiResponse(itemService.findAll()));
    }

}
