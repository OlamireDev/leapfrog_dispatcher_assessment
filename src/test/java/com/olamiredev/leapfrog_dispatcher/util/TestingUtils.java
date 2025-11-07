package com.olamiredev.leapfrog_dispatcher.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class TestingUtils {

    public static String baseUrl(int serverPort) {
        return "http://localhost:" + serverPort + "/api/v1/box";
    }

    public static HttpHeaders createJsonHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

}
