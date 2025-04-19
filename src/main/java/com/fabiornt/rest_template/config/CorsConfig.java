package com.fabiornt.rest_template.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Configuration class for CORS (Cross-Origin Resource Sharing) settings.
 * This allows the API to be accessed from different origins.
 */
@Configuration
public class CorsConfig {

    @Value("${cors.allowed-origins:*}")
    private String allowedOrigins;

    @Value("${cors.allowed-methods:GET,POST,PUT,PATCH,DELETE,OPTIONS}")
    private String allowedMethods;

    @Value("${cors.allowed-headers:Origin,Content-Type,Accept,Authorization,X-Requested-With}")
    private String allowedHeaders;

    @Value("${cors.exposed-headers:Access-Control-Allow-Origin,Access-Control-Allow-Credentials,Authorization}")
    private String exposedHeaders;

    @Value("${cors.allow-credentials:true}")
    private Boolean allowCredentials;

    @Value("${cors.max-age:3600}")
    private Long maxAge;

    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();

        // Allow specified origins or all origins with pattern
        if ("*".equals(allowedOrigins)) {
            config.setAllowedOriginPatterns(Collections.singletonList("*"));
        } else {
            config.setAllowedOrigins(Arrays.asList(allowedOrigins.split(",")));
        }

        // Allow credentials (cookies, authorization headers, etc.)
        config.setAllowCredentials(allowCredentials);

        // Allow specified HTTP methods
        config.setAllowedMethods(Arrays.asList(allowedMethods.split(",")));

        // Allow specified headers
        config.setAllowedHeaders(Arrays.asList(allowedHeaders.split(",")));

        // Expose specified headers that clients are allowed to access
        config.setExposedHeaders(Arrays.asList(exposedHeaders.split(",")));

        // How long the browser should cache the CORS response (in seconds)
        config.setMaxAge(maxAge);

        // Apply this configuration to all paths
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
