package com.vonk.storyquest.controller;

import com.vonk.storyquest.model.User;
import com.vonk.storyquest.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.findByUsername("Sayu").isPresent() ? userService.findByUsername("Sayu").stream().toList() : List.of();
    }
}
