package com.pro.task_management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class WebConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        List<String> allowedMethods = Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS");

        config.addAllowedOrigin("http://localhost:5173");
        config.addAllowedOrigin("http://localhost:3000");
        config.addAllowedOrigin("https://frontend-project-management-lac.vercel.app");

        config.setAllowedMethods(allowedMethods);

//        Header that the client is allowed to send in the actual request
        config.setAllowedHeaders(Arrays.asList(
                "Authorization",      // JWT token
                "Content-Type",       // application/json
                "Accept",
                "X-Requested-With",
                "Cache-Control"
        ));

//        Header that the client is allowed to access in the response
        config.setExposedHeaders(Arrays.asList(
                "Authorization",      // JWT token
                "Content-Type",       // application/json
                "Accept",
                "X-Requested-With",
                "Cache-Control",
                "X-Total-Count",   // For pagination
                "X-Page-Number"
        ));


//        If your API requires credentials (like cookies or HTTP authentication), you need to set this to true.
        config.setAllowCredentials(true);

        config.setMaxAge(3600L); // Cache preflight response for 1 hour

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // Apply CORS configuration to all endpoints
        return source;
    }
}
