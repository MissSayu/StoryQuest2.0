package com.vonk.storyquest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsAndStaticConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:5173")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }

            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                Path uploadDir = Paths.get("storyquest/src/main/resources/static/uploads");
                String uploadPath = uploadDir.toFile().getAbsolutePath();
                System.out.println("✅ Serving uploads from: " + uploadPath);
                registry.addResourceHandler("/uploads/**")
                        .addResourceLocations("file:" + uploadPath + "/");


                Path avatarsDir = Paths.get("storyquest/src/main/resources/static/avatars");
                String avatarsPath = avatarsDir.toFile().getAbsolutePath();
                System.out.println("✅ Serving avatars from: " + avatarsPath);
                registry.addResourceHandler("/avatars/**")
                        .addResourceLocations("file:" + avatarsPath + "/");
            }
        };
    }
}
