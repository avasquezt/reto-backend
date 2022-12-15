package com.reto_backend.reto_backend.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI Configuration for Swagger UI
 *
 * @author Marcus Cvjeticanin
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI(
    ) {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("Reto Backend")
                        .description("API to manage test appointments")
                );
    }
}
