package com.vonk.storyquest;

import com.vonk.storyquest.model.User;
import com.vonk.storyquest.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class StoryquestApplication {

    public static void main(String[] args) {
        SpringApplication.run(StoryquestApplication.class, args);
    }

    @Bean
    CommandLineRunner run(UserService userService) {
        return args -> {
            if (userService.findByUsername("Sayu").isEmpty()) {
                userService.saveUser(new User("Sayu", "Password123", "USER"));
            }
            if (userService.findByUsername("Virelight").isEmpty()) {
                userService.saveUser(new User("Virelight", "Mod123", "MOD"));
            }
        };
    }
}