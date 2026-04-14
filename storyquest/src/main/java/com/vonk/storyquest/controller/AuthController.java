package com.vonk.storyquest.controller;

import com.vonk.storyquest.dto.UserDTO;
import com.vonk.storyquest.model.User;
import com.vonk.storyquest.security.JwtUtil;
import com.vonk.storyquest.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            User newUser = userService.registerUser(
                    user.getUsername(),
                    user.getEmail(),
                    user.getPassword()
            );

            return ResponseEntity.ok(new UserDTO(newUser));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        User user = userService.findByUsername(username).orElse(null);
        if (user == null || !userService.checkPassword(password, user.getPassword())) {
            return ResponseEntity.status(400)
                    .body(Map.of("message", "Invalid username or password"));
        }

        String token = jwtUtil.generateToken(username);

        String avatarUrl = "http://localhost:8081/avatars/default.jpg";
        String bio = "This user hasn’t written a bio yet.";

        if (user.getProfile() != null) {
            if (user.getProfile().getAvatarUrl() != null) {
                avatarUrl = user.getProfile().getAvatarUrl();
            }
            if (user.getProfile().getBio() != null) {
                bio = user.getProfile().getBio();
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("id", user.getId());
        response.put("username", user.getUsername());
        response.put("role", user.getRole());
        response.put("isMod", "MOD".equalsIgnoreCase(user.getRole()));
        response.put("avatarUrl", avatarUrl);
        response.put("bio", bio);
        response.put("token", token);

        return ResponseEntity.ok(response);
    }
}
