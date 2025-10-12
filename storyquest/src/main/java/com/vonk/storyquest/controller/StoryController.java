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

@RestController
@RequestMapping("/api/stories")
@CrossOrigin(origins = "http://localhost:5173")
public class StoryController {

    private final StoryService storyService;
    private final EpisodeService episodeService; // 👈 Add this
    private final UserRepository userRepository;

    public StoryController(StoryService storyService, EpisodeService episodeService, UserRepository userRepository) {
        this.storyService = storyService;
        this.episodeService = episodeService; // 👈 Initialize it
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

    // ===== Create story =====
    @PostMapping
    public ResponseEntity<Story> createStory(@RequestParam Long userId, @RequestBody Story story) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        story.setUser(user);
        story.setStatus("draft");
        Story createdStory = storyService.createStory(story);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStory);
    }

    // ===== Add episode to story =====
    @PostMapping("/{storyId}/episodes")
    public ResponseEntity<?> addEpisode(@PathVariable Long storyId, @RequestBody Episode episode) {
        try {
            Episode createdEpisode = episodeService.addEpisode(storyId, episode); // 👈 FIXED HERE
            return ResponseEntity.status(HttpStatus.CREATED).body(createdEpisode);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Story not found");
        }
    }

    // ===== Get all episodes =====
    @GetMapping("/{storyId}/episodes")
    public ResponseEntity<?> getEpisodes(@PathVariable Long storyId) {
        try {
            List<Episode> episodes = episodeService.getEpisodesByStory(storyId); // 👈 FIXED HERE
            return ResponseEntity.ok(episodes);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Story not found");
        }
    }
}
