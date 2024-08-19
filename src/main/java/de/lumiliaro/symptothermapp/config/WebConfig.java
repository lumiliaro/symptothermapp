package de.lumiliaro.symptothermapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173") // Erlaubt Anfragen von localhost:5173
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Erlaubt die angegebenen Methoden
                .allowedHeaders("*") // Erlaubt alle Header
                .allowCredentials(true); // Erlaubt Cookies und Authentifizierungsdaten
    }
}
