package com.vonk.storyquest.controller;
import com.vonk.storyquest.model.User;
import com.vonk.storyquest.security.JwtUtil;
import com.vonk.storyquest.service.UserService;
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

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        User user = userService.findByUsername(username).orElse(null);
        if (user == null) {
            return ResponseEntity.status(400).body(Map.of("message", "Invalid username or password"));
        }


        if (!userService.checkPassword(password, user.getPassword())) {
            return ResponseEntity.status(400).body(Map.of("message", "Invalid username or password"));
        }


        String token = jwtUtil.generateToken(username);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Login successful");
        response.put("token", token);
        response.put("role", user.getRole());
        response.put("isMod", "MOD".equalsIgnoreCase(user.getRole()));

        return ResponseEntity.ok(response);
    }
}
