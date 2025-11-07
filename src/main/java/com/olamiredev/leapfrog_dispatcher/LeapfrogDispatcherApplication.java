package com.olamiredev.leapfrog_dispatcher;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.olamiredev.leapfrog_dispatcher.data.dto.CreateBoxRequestDTO;
import com.olamiredev.leapfrog_dispatcher.data.entity.ItemEntity;
import com.olamiredev.leapfrog_dispatcher.repository.ItemRepository;
import com.olamiredev.leapfrog_dispatcher.service.BoxManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.InputStream;
import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class LeapfrogDispatcherApplication implements CommandLineRunner {

    private final BoxManagementService boxManagementService;

    private final ObjectMapper objectMapper;

    private final ItemRepository itemRepository;

    public static void main(String[] args) {
        SpringApplication.run(LeapfrogDispatcherApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        InputStream inputStream = getClass().getResourceAsStream("/boxes.json");
        var boxes = objectMapper.readValue(inputStream, new TypeReference<List<CreateBoxRequestDTO>>() {});
        boxes.forEach(boxManagementService::createBox);
        inputStream = getClass().getResourceAsStream("/items.json");
        var items = objectMapper.readValue(inputStream, new TypeReference<List<ItemEntity>>() {});
        itemRepository.saveAll(items);
    }

}
