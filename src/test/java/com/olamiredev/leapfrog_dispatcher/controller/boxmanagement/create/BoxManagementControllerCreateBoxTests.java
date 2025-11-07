package com.olamiredev.leapfrog_dispatcher.controller.boxmanagement.create;

import com.olamiredev.leapfrog_dispatcher.data.dto.BoxResourceDTO;
import com.olamiredev.leapfrog_dispatcher.data.dto.CreateBoxRequestDTO;
import com.olamiredev.leapfrog_dispatcher.data.dto.LeapFrogApiResponse;
import com.olamiredev.leapfrog_dispatcher.data.dto.LeapFrogErrorResponse;
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

import java.math.BigDecimal;
import java.util.Map;

import static com.olamiredev.leapfrog_dispatcher.data.discrete.BoxDispatchState.IDLE;
import static com.olamiredev.leapfrog_dispatcher.util.TestingUtils.baseUrl;
import static com.olamiredev.leapfrog_dispatcher.util.TestingUtils.createJsonHeaders;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BoxManagementControllerCreateBoxTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void createBoxSuccessfulRequest() {
        var headers = createJsonHeaders();
        var requestDTO = new CreateBoxRequestDTO("qR1tW5vP9fM2bS8yK5nK", new BigDecimal("188.75") ,45, IDLE);
        var httpEntity = new HttpEntity<>(requestDTO, headers);
        var response = restTemplate.exchange(baseUrl(port), HttpMethod.POST, httpEntity,
                new ParameterizedTypeReference<LeapFrogApiResponse<BoxResourceDTO>>(){});
        assertEquals(response.getStatusCode().value(), HttpStatus.CREATED.value());
    }

    @Test
    void createBoxFailedBecauseAlreadyExists() {
        var headers = createJsonHeaders();
        var requestDTO = new CreateBoxRequestDTO("qR1tW5vP9fM2bS8yK4nL", new BigDecimal("188.75") ,45, IDLE);
        var httpEntity = new HttpEntity<>(requestDTO, headers);
        var response = restTemplate.exchange(baseUrl(port), HttpMethod.POST, httpEntity,
                new ParameterizedTypeReference<LeapFrogApiResponse<LeapFrogErrorResponse>>(){});
        assertEquals(response.getStatusCode().value(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        var responseBody = response.getBody();
        assertEquals("Error", responseBody.getMessage());
        assertTrue(responseBody.getData().getErrorMessage().contains("Box already exists for txRef"));
    }

    @Test
    void createBoxFailedBecauseInvalidState() {
        var headers = createJsonHeaders();
        Map<String, Object> requestDTO = Map.of("txRef", "qR1tW5vP9fM2bS8yK6nL", "weight", 188.75, "batteryLevel", 45,
                "state", "IDLE2");
        var httpEntity = new HttpEntity<>(requestDTO, headers);
        var response = restTemplate.exchange(baseUrl(port), HttpMethod.POST, httpEntity,
                new ParameterizedTypeReference<LeapFrogApiResponse<LeapFrogErrorResponse>>(){});
        assertEquals(response.getStatusCode().value(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        var responseBody = response.getBody();
        assertEquals("Error", responseBody.getMessage());
        assertTrue(responseBody.getData().getErrorMessage().contains("not one of the values accepted for Enum class:"));
    }

}