package com.vonk.storyquest.service;

import com.vonk.storyquest.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean login(String username, String password) {
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return passwordEncoder.matches(password, user.getPassword());
    }

    // Temporary method to reset Lumine's password
    public void resetLuminePassword() {
        User user = userService.findByUsername("Lumine")
                .orElseThrow(() -> new RuntimeException("Lumine not found"));

        String rawPassword = "Fire123"; // new temporary password
        String encoded = passwordEncoder.encode(rawPassword);
        user.setPassword(encoded);

        userService.saveUser(user);

        System.out.println("Lumine's password has been reset!");
    }
}
