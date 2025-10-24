package com.vonk.storyquest.controller;

import com.vonk.storyquest.dto.StoryDTO;
import com.vonk.storyquest.model.Episode;
import com.vonk.storyquest.model.Story;
import com.vonk.storyquest.model.User;
import com.vonk.storyquest.repository.StoryRepository;
import com.vonk.storyquest.repository.UserRepository;
import com.vonk.storyquest.service.EpisodeService;
import com.vonk.storyquest.service.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/stories")
@CrossOrigin(origins = "http://localhost:5173")
public class StoryController implements WebMvcConfigurer {

    @Autowired
    private StoryService storyService;

    @Autowired
    private EpisodeService episodeService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StoryRepository storyRepository;

    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/storyquest/src/main/resources/static/uploads";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + UPLOAD_DIR + "/");
    }

    // Create story or add episode
    @PostMapping("/create")
    public ResponseEntity<?> createStory(
            @RequestParam(required = false) Long storyId,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String content,
            @RequestParam(value = "coverImage", required = false) MultipartFile coverImage
    ) {
        try {
            // Adding an episode to an existing story
            if (storyId != null) {
                Story parentStory = storyService.getStoryById(storyId);
                if (parentStory == null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("Parent story not found with ID: " + storyId);
                }

                Episode episode = new Episode();
                episode.setTitle(title != null ? title : "Episode " + (parentStory.getEpisodes().size() + 1));
                episode.setContent(content != null ? content : "");
                episode.setEpisodeOrder(parentStory.getEpisodes().size() + 1);
                episode.setStory(parentStory);

                episodeService.save(episode);
                return ResponseEntity.ok(episode);
            }

            // Creating a new story
            String coverImageUrl = null;
            if (coverImage != null && !coverImage.isEmpty()) {
                File uploadPath = new File(UPLOAD_DIR + "/covers");
                if (!uploadPath.exists()) uploadPath.mkdirs();

                String fileName = System.currentTimeMillis() + "_" + coverImage.getOriginalFilename();
                Path filePath = Paths.get(uploadPath.getAbsolutePath(), fileName);
                Files.copy(coverImage.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                coverImageUrl = "http://localhost:8081/uploads/covers/" + fileName;
            }

            Story newStory = storyService.createStory(title, description, type, userId, coverImageUrl, status);
            return ResponseEntity.ok(new StoryDTO(newStory));

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload image: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Failed to create story/episode: " + e.getMessage());
        }
    }

    // Get all stories by a user ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<StoryDTO>> getStoriesByUserId(@PathVariable Long userId) {
        List<Story> stories = storyService.getStoriesByUserIdIncludingDrafts(userId);
        List<StoryDTO> dtos = stories.stream().map(StoryDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // Get a story by its ID
    @GetMapping("/{id}")
    public ResponseEntity<StoryDTO> getStoryById(@PathVariable Long id) {
        Story story = storyService.getStoryById(id);
        return ResponseEntity.ok(new StoryDTO(story));
    }

    // Get stories by username
    @GetMapping("/username/{username}")
    public ResponseEntity<List<StoryDTO>> getStoriesByUsername(@PathVariable String username) {
        List<Story> stories = storyService.getStoriesByUsername(username);
        List<StoryDTO> dtos = stories.stream().map(StoryDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // Get all stories
    @GetMapping
    public ResponseEntity<List<StoryDTO>> getAllStories() {
        List<Story> stories = storyService.getAllStories();
        List<StoryDTO> dtos = stories.stream().map(StoryDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // Get random stories
    @GetMapping("/random")
    public ResponseEntity<List<StoryDTO>> getRandomStories(@RequestParam(defaultValue = "3") int count) {
        List<Story> stories = storyService.getRandomStories(count);
        List<StoryDTO> dtos = stories.stream().map(StoryDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // Count stories by user
    @GetMapping("/count/{userId}")
    public ResponseEntity<Long> countStories(@PathVariable Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        long count = storyRepository.countByUser(user); // fixed to match Story entity
        return ResponseEntity.ok(count);
    }
}
