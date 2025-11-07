package com.olamiredev.leapfrog_dispatcher.controller.boxmanagement.load;

import com.olamiredev.leapfrog_dispatcher.data.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.util.Set;

import static com.olamiredev.leapfrog_dispatcher.util.TestingUtils.baseUrl;
import static com.olamiredev.leapfrog_dispatcher.util.TestingUtils.createJsonHeaders;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BoxManagementControllerLoadBoxTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void loadBoxSuccessfulRequest() {
        var headers = createJsonHeaders();
        Set<UpdateItemSingleRequestDTO> itemsToAdd = Set.of(new UpdateItemSingleRequestDTO(1L, 2),
                new UpdateItemSingleRequestDTO(2L, 2));
        var requestDTO = new UpdateBoxLoadRequestDTO(itemsToAdd);
        var httpEntity = new HttpEntity<>(requestDTO, headers);
        var response = restTemplate.exchange(baseUrl(port) + "/xQ7mP2aL9dT0wF3rN8bK/load", HttpMethod.PUT, httpEntity,
                new ParameterizedTypeReference<LeapFrogApiResponse<LoadedBoxItemsResponseDTO>>(){});
        assertEquals(response.getStatusCode().value(), HttpStatus.OK.value());
    }

    @Test
    void loadBoxFailedBecauseOfOverloadOnSecondRequest() {
        var headers = createJsonHeaders();
        Set<UpdateItemSingleRequestDTO> itemsToAdd = Set.of(new UpdateItemSingleRequestDTO(1L, 2),
                new UpdateItemSingleRequestDTO(2L, 2));
        var requestDTO = new UpdateBoxLoadRequestDTO(itemsToAdd);
        var httpEntity = new HttpEntity<>(requestDTO, headers);
        restTemplate.exchange(baseUrl(port) + "/xQ7mP2aL9dT0wF3rN8bK/load", HttpMethod.PUT, httpEntity,
                new ParameterizedTypeReference<LeapFrogApiResponse<LoadedBoxItemsResponseDTO>>(){});
        var failedResponse = restTemplate.exchange(baseUrl(port) + "/xQ7mP2aL9dT0wF3rN8bK/load", HttpMethod.PUT, httpEntity,
                new ParameterizedTypeReference<LeapFrogApiResponse<LeapFrogErrorResponse>>(){});
        assertEquals(failedResponse.getStatusCode().value(), HttpStatus.BAD_REQUEST.value());
        var responseBody = failedResponse.getBody();
        assertEquals("Error", responseBody.getMessage());
        assertTrue(responseBody.getData().getErrorMessage().contains("is greater than box max weight"));
    }

    @Test
    void loadBoxFailedBecauseBoxNotFound() {
        var headers = createJsonHeaders();
        Set<UpdateItemSingleRequestDTO> itemsToAdd = Set.of(new UpdateItemSingleRequestDTO(1L, 2),
                new UpdateItemSingleRequestDTO(2L, 2));
        var requestDTO = new UpdateBoxLoadRequestDTO(itemsToAdd);
        var httpEntity = new HttpEntity<>(requestDTO, headers);
        var failedResponse = restTemplate.exchange(baseUrl(port) + "/xQ7mP2aL9dT0wF3rN8b9/load", HttpMethod.PUT, httpEntity,
                new ParameterizedTypeReference<LeapFrogApiResponse<LeapFrogErrorResponse>>(){});
        assertEquals(failedResponse.getStatusCode().value(), HttpStatus.NOT_FOUND.value());
        var responseBody = failedResponse.getBody();
        assertEquals("Error", responseBody.getMessage());
        assertTrue(responseBody.getData().getErrorMessage().contains("not found"));
    }

}