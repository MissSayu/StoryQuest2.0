package com.vonk.storyquest.controller;

import com.vonk.storyquest.model.Episode;
import com.vonk.storyquest.model.Story;
import com.vonk.storyquest.model.User;
import com.vonk.storyquest.service.EpisodeService;
import com.vonk.storyquest.service.StoryService;
import com.vonk.storyquest.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/stories")
@CrossOrigin(origins = "http://localhost:5173")
public class StoryController {

    private final StoryService storyService;
    private final EpisodeService episodeService;
    private final UserRepository userRepository;

    public StoryController(StoryService storyService, EpisodeService episodeService, UserRepository userRepository) {
        this.storyService = storyService;
        this.episodeService = episodeService;
        this.userRepository = userRepository;
    }

    // ===== Get all stories =====
    @GetMapping
    public ResponseEntity<List<Story>> getAllStories() {
        return ResponseEntity.ok(storyService.getAllStories());
    }

    // ===== Get single story =====
    @GetMapping("/{id}")
    public ResponseEntity<?> getStory(@PathVariable Long id) {
        Optional<Story> story = storyService.getStoryById(id);
        return story.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Story not found"));
    }

    // ===== Get all stories by user =====
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getStoriesByUser(@PathVariable Long userId) {
        List<Story> stories = storyService.getStoriesByUser(userId);
        return ResponseEntity.ok(stories);
    }

    // ===== Create story with file uploads =====
    @PostMapping
    public ResponseEntity<Story> createStory(
            @RequestParam Long userId,
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam String type,
            @RequestParam String genre,
            @RequestParam(required = false) MultipartFile coverImage,
            @RequestParam(required = false) MultipartFile comicFile
    ) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Story story = new Story();
        story.setTitle(title);
        story.setDescription(description);
        story.setType(type);
        story.setGenre(genre);
        story.setUser(user);
        story.setStatus("draft");

        try {
            String uploadDir = "uploads/stories/" + System.currentTimeMillis() + "/";
            new File(uploadDir).mkdirs();

            if (coverImage != null && !coverImage.isEmpty()) {
                String coverName = System.currentTimeMillis() + "_" + coverImage.getOriginalFilename();
                coverImage.transferTo(new File(uploadDir + coverName));
                story.setCoverImage("/" + uploadDir + coverName);
            }

            if (comicFile != null && !comicFile.isEmpty()) {
                String comicName = System.currentTimeMillis() + "_" + comicFile.getOriginalFilename();
                comicFile.transferTo(new File(uploadDir + comicName));
                story.setComicFilePath("/" + uploadDir + comicName);
            }

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }

        Story createdStory = storyService.createStory(story);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStory);
    }

    // ===== Add episode to story =====
    @PostMapping("/{storyId}/episodes")
    public ResponseEntity<?> addEpisode(@PathVariable Long storyId, @RequestBody Episode episode) {
        try {
            Episode createdEpisode = episodeService.addEpisode(storyId, episode);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdEpisode);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Story not found");
        }
    }

    // ===== Get all episodes =====
    @GetMapping("/{storyId}/episodes")
    public ResponseEntity<?> getEpisodes(@PathVariable Long storyId) {
        try {
            List<Episode> episodes = episodeService.getEpisodesByStory(storyId);
            return ResponseEntity.ok(episodes);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Story not found");
        }
    }

    // ===== Upload cover or comic files for existing story =====
    @PostMapping("/{id}/upload")
    public ResponseEntity<?> uploadStoryFiles(
            @PathVariable Long id,
            @RequestParam(required = false) MultipartFile coverImage,
            @RequestParam(required = false) MultipartFile comicFile) {

        Optional<Story> optionalStory = storyService.getStoryById(id);
        if (optionalStory.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Story not found");
        }

        Story story = optionalStory.get();

        try {
            String uploadDir = "uploads/stories/" + story.getId() + "/";
            new File(uploadDir).mkdirs();

            if (coverImage != null && !coverImage.isEmpty()) {
                String coverName = System.currentTimeMillis() + "_" + coverImage.getOriginalFilename();
                coverImage.transferTo(new File(uploadDir + coverName));
                story.setCoverImage("/" + uploadDir + coverName);
            }

            if (comicFile != null && !comicFile.isEmpty()) {
                String comicName = System.currentTimeMillis() + "_" + comicFile.getOriginalFilename();
                comicFile.transferTo(new File(uploadDir + comicName));
                story.setComicFilePath("/" + uploadDir + comicName);
            }

            storyService.updateStory(story);
            return ResponseEntity.ok("Files uploaded successfully.");

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed: " + e.getMessage());
        }
    }

    // ===== Publish story =====
    @PutMapping("/{id}/publish")
    public ResponseEntity<?> publishStory(@PathVariable Long id) {
        Optional<Story> optionalStory = storyService.getStoryById(id);
        if (optionalStory.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Story not found");
        }

        Story story = optionalStory.get();
        story.setStatus("published");
        story.setPublishedAt(LocalDateTime.now());
        storyService.updateStory(story);

        return ResponseEntity.ok("Story published successfully");
    }
}
