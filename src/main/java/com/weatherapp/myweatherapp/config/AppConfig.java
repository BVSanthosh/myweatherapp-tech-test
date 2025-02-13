package com.weatherapp.myweatherapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration class responsible for holding configuration settings and bean definitions.
 */
@Configuration
public class AppConfig {

    /**
     * Creates a RestTemplate bean that is used in the Repositry layer for making HTTP requests to the Visual Crossing API.
     * 
     * @return A configured instance for RestTemplate
     */
    @Bean  
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
