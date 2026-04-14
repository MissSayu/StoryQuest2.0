package com.vonk.storyquest.service;

import com.vonk.storyquest.dto.UserDTO;
import com.vonk.storyquest.model.Profile;
import com.vonk.storyquest.model.User;
import com.vonk.storyquest.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private static final String DEFAULT_BIO = "This user hasn’t written a bio yet.";
    private static final String DEFAULT_AVATAR_URL = "http://localhost:8081/avatars/default.jpg";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User saveUserWithoutChangingPassword(User user) {
        return userRepository.save(user);
    }

    public User registerUser(String username, String email, String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        if (email != null && !email.isBlank() && userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("USER");

        Profile profile = new Profile();
        profile.setBio(DEFAULT_BIO);
        profile.setAvatarUrl(DEFAULT_AVATAR_URL);
        profile.setUser(user);

        user.setProfile(profile);

        return userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public User getUserOrNull(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public Optional<User> updateUser(Long id, UserDTO updatedUser) {
        return userRepository.findById(id).map(existingUser -> {
            if (updatedUser.getUsername() != null && !updatedUser.getUsername().isBlank()) {
                existingUser.setUsername(updatedUser.getUsername());
            }

            if (updatedUser.getEmail() != null) {
                existingUser.setEmail(updatedUser.getEmail());
            }

            if (existingUser.getProfile() == null) {
                Profile profile = new Profile();
                profile.setBio(DEFAULT_BIO);
                profile.setAvatarUrl(DEFAULT_AVATAR_URL);
                profile.setUser(existingUser);
                existingUser.setProfile(profile);
            }

            if (updatedUser.getBio() != null) {
                existingUser.getProfile().setBio(updatedUser.getBio());
            }

            if (updatedUser.getAvatarUrl() != null && !updatedUser.getAvatarUrl().isBlank()) {
                existingUser.getProfile().setAvatarUrl(updatedUser.getAvatarUrl());
            }

            return userRepository.save(existingUser);
        });
    }
}
