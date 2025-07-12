package com.fabiornt.rest_template.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

/**
 * Configuration for OpenAPI documentation.
 */
@Configuration
public class OpenApiConfig {

    /**
     * Configures the OpenAPI documentation.
     * 
     * @return The OpenAPI configuration
     */
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("REST Template API")
                        .description("A level 3 maturity model REST API template with JWT authentication")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Fabio Ribeiro")
                                .url("https://github.com/fabiornt")
                                .email("fabiornt@example.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .addSecurityItem(new SecurityRequirement().addList("JWT"))
                .components(new Components()
                        .addSecuritySchemes("JWT", new SecurityScheme()
                                .name("JWT")
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("Enter JWT token with Bearer prefix (e.g. 'Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...')")));
    }
}