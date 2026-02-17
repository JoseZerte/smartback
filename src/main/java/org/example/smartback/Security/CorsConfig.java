package org.example.smartback.Security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                // Permitimos localhost (PC) y tu IP (Móvil)
                .allowedOriginPatterns(
                        "http://localhost:4200",
                        "http://192.168.0.193:4200",
                        "http://192.168.0.193:8100", // Por si Ionic usa el puerto 8100
                        "http://192.168.0.193:*"      // Comodín para cualquier puerto en tu IP
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}