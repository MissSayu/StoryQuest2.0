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

    CommandLineRunner run(UserService userService, PasswordEncoder encoder) {
        return args -> {
            if(userService.findByUsername("Sayu") == null) {
                userService.saveUser(new User("Sayu", encoder.encode("Password123"), "USER"));
            }
            if(userService.findByUsername("Virelight") == null) {
                userService.saveUser(new User("Virelight", encoder.encode("Mod123"), "MOD"));
            }
        };
    }
}
