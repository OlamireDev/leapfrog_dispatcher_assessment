package com.olamiredev.leapfrog_dispatcher.config.api;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI baseApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Leap Frog Box Delivery Service APIs")
                        .description("API for managing autonomous delivery boxes and items")
                        .version("v1.0"));
    }

}
