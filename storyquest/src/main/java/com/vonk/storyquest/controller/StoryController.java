package com.vonk.storyquest.controller;

import com.vonk.storyquest.dto.StoryDTO;
import com.vonk.storyquest.model.Episode;
import com.vonk.storyquest.model.Story;
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

    private static final String UPLOAD_DIR =
            System.getProperty("user.dir") + "/src/main/resources/static/uploads";
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5 MB

    @Autowired
    private StoryService storyService;

    @Autowired
    private EpisodeService episodeService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StoryRepository storyRepository;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + UPLOAD_DIR + "/");
    }

    @PostMapping("/create")
    public ResponseEntity<?> createStory(
            @RequestParam(name = "storyId", required = false) Long storyId,
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "description", required = false) String description,
            @RequestParam(name = "genre", required = false) String genre,
            @RequestParam(name = "type", required = false) String type,
            @RequestParam(name = "userId", required = false) Long userId,
            @RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "storyContent", required = false) String storyContent,
            @RequestParam(name = "coverImage", required = false) MultipartFile coverImage
    ) {
        try {
            if (storyId != null) {
                Story parentStory = storyService.getStoryById(storyId);
                if (parentStory == null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("Parent story not found with ID: " + storyId);
                }

                Episode episode = new Episode();
                episode.setTitle(title != null ? title : "Episode " + (parentStory.getEpisodes().size() + 1));
                episode.setContent(storyContent != null ? storyContent : "");
                episode.setEpisodeOrder(parentStory.getEpisodes().size() + 1);
                episode.setStory(parentStory);

                if (episode.getTitle().length() > 255) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("Episode title is te lang (max 255 tekens)");
                }

                episodeService.save(episode);
                return ResponseEntity.ok(episode);
            }

            String coverImageUrl = null;
            if (coverImage != null && !coverImage.isEmpty()) {
                if (coverImage.getSize() > MAX_FILE_SIZE) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("Cover image is te groot (max 5 MB)");
                }

                File uploadPath = new File(UPLOAD_DIR + "/covers");
                if (!uploadPath.exists()) {
                    uploadPath.mkdirs();
                }

                String fileName = System.currentTimeMillis() + "_" + coverImage.getOriginalFilename();
                Path filePath = Paths.get(uploadPath.getAbsolutePath(), fileName);
                Files.copy(coverImage.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                coverImageUrl = "/uploads/covers/" + fileName;
            }

            if (genre == null || genre.isBlank()) genre = "Unknown";
            if (type == null || type.isBlank()) type = "story";

            if (title != null && title.length() > 255) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Story title is te lang (max 255 tekens)");
            }

            if (description != null && description.length() > 2000) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Description is te lang (max 2000 tekens)");
            }

            Story newStory = storyService.createStory(
                    title,
                    description,
                    genre,
                    type,
                    userId,
                    coverImageUrl,
                    status,
                    storyContent
            );

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

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<StoryDTO>> getStoriesByUserId(@PathVariable("userId") Long userId) {
        List<Story> stories = storyService.getStoriesByUserIdIncludingDrafts(userId);
        List<StoryDTO> dtos = stories.stream().map(StoryDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StoryDTO> getStoryById(@PathVariable("id") Long id) {
        Story story = storyService.getStoryById(id);
        return ResponseEntity.ok(new StoryDTO(story));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<List<StoryDTO>> getStoriesByUsername(@PathVariable("username") String username) {
        List<Story> stories = storyService.getStoriesByUsername(username);
        List<StoryDTO> dtos = stories.stream().map(StoryDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping
    public ResponseEntity<List<StoryDTO>> getAllStories() {
        List<Story> stories = storyService.getAllStories();
        List<StoryDTO> dtos = stories.stream().map(StoryDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/random")
    public ResponseEntity<List<StoryDTO>> getRandomStories(
            @RequestParam(name = "count", defaultValue = "3") int count
    ) {
        List<Story> stories = storyService.getRandomStories(count);
        List<StoryDTO> dtos = stories.stream().map(StoryDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
}