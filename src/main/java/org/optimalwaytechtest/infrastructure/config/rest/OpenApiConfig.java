package org.optimalwaytechtest.infrastructure.config.rest;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "OptimalRoom API",
                version = "1.0",
                description = "API documentation for the Optimal Room application"
        )
)
public class OpenApiConfig {
}