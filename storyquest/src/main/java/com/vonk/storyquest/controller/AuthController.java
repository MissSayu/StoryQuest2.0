package com.vonk.storyquest.controller;
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


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            User newUser = userService.registerUser(user.getUsername(), user.getEmail(), user.getPassword());

            newUser.setPassword(null);
            return ResponseEntity.ok(newUser);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        System.out.println("Login attempt for username: '" + username + "' with password: '" + password + "'");

        User user = userService.findByUsername(username).orElse(null);
        if (user == null) {
            System.out.println("User not found in DB");
            return ResponseEntity.status(400).body(Map.of("message", "Invalid username or password"));
        }

        System.out.println("Found user: " + user.getUsername());
        System.out.println("Stored password hash: " + user.getPassword());

        boolean pwMatches = userService.checkPassword(password, user.getPassword());
        System.out.println("Password matches? " + pwMatches);

        if (!pwMatches) {
            return ResponseEntity.status(400).body(Map.of("message", "Invalid username or password"));
        }

        String token = jwtUtil.generateToken(username);
        System.out.println("Login successful, token: " + token);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Login successful");
        response.put("token", token);
        response.put("role", user.getRole());
        response.put("isMod", "MOD".equalsIgnoreCase(user.getRole()));

        return ResponseEntity.ok(response);
    }

}
