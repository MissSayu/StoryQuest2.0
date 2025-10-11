package com.vonk.storyquest.controller;

import com.vonk.storyquest.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // of je frontend URL
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        boolean success = userService.checkPassword(username, password);
        if (success) {
            return "Login successful"; // later kun je token of sessie teruggeven
        } else {
            return "Invalid username or password";
        }
    }
}
