package com.vonk.storyquest.controller;

import com.vonk.storyquest.dto.UserDTO;
import com.vonk.storyquest.model.Profile;
import com.vonk.storyquest.model.User;
import com.vonk.storyquest.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/upload")
@CrossOrigin(origins = "http://localhost:5173")
public class AvatarUploadController {

    private final UserService userService;

    public AvatarUploadController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/avatar/{userId}")
    public ResponseEntity<?> uploadAvatar(@PathVariable Long userId,
                                          @RequestParam("file") MultipartFile file) {
        try {
            User user = userService.getUserById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            String projectDir = System.getProperty("user.dir");
            String avatarsDirPath = projectDir + "/src/main/resources/static/avatars";
            File dir = new File(avatarsDirPath);

            if (!dir.exists() && !dir.mkdirs()) {
                throw new RuntimeException("Could not create upload directory: " + avatarsDirPath);
            }

            String originalFilename = file.getOriginalFilename() != null
                    ? file.getOriginalFilename().replaceAll("[^a-zA-Z0-9\\.\\-]", "_")
                    : "avatar.jpg";

            String fileName = "avatar_" + userId + "_" + System.currentTimeMillis() + "_" + originalFilename;
            File destination = Paths.get(dir.getAbsolutePath(), fileName).toFile();

            file.transferTo(destination);

            if (user.getProfile() == null) {
                Profile profile = new Profile();
                profile.setUser(user);
                user.setProfile(profile);
            }

            user.getProfile().setAvatarUrl("/avatars/" + fileName);
            User savedUser = userService.saveUserWithoutChangingPassword(user);

            return ResponseEntity.ok(new UserDTO(savedUser));

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Upload failed: " + e.getMessage());
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(400).body("Error: " + e.getMessage());
        }
    }
}
