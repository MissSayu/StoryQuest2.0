package com.vonk.storyquest.controller;

import com.vonk.storyquest.dto.EpisodeDTO;
import com.vonk.storyquest.dto.StoryDTO;
import com.vonk.storyquest.dto.UserDTO;
import com.vonk.storyquest.model.Episode;
import com.vonk.storyquest.model.Story;
import com.vonk.storyquest.repository.EpisodeRepository;
import com.vonk.storyquest.repository.StoryRepository;
import com.vonk.storyquest.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/search")
@CrossOrigin(origins = "http://localhost:5173")
public class SearchController {

    private final StoryRepository storyRepository;
    private final UserRepository userRepository;
    private final EpisodeRepository episodeRepository;

    public SearchController(
            StoryRepository storyRepository,
            UserRepository userRepository,
            EpisodeRepository episodeRepository
    ) {
        this.storyRepository = storyRepository;
        this.userRepository = userRepository;
        this.episodeRepository = episodeRepository;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> search(@RequestParam("q") String q) {
        String query = q == null ? "" : q.trim();

        if (query.isBlank()) {
            Map<String, Object> empty = new HashMap<>();
            empty.put("users", Collections.emptyList());
            empty.put("stories", Collections.emptyList());
            empty.put("episodes", Collections.emptyList());
            return ResponseEntity.ok(empty);
        }

        String lowerQuery = query.toLowerCase();

        List<UserDTO> users = userRepository.findByUsernameContainingIgnoreCase(query)
                .stream()
                .map(UserDTO::new)
                .collect(Collectors.toList());

        Set<Long> addedStoryIds = new HashSet<>();
        List<StoryDTO> storyResults = new ArrayList<>();

        List<Story> allStories = storyRepository.findAll();
        for (Story story : allStories) {
            boolean matchesTitle =
                    story.getTitle() != null &&
                            story.getTitle().toLowerCase().contains(lowerQuery);

            boolean matchesDescription =
                    story.getDescription() != null &&
                            story.getDescription().toLowerCase().contains(lowerQuery);

            if (matchesTitle || matchesDescription) {
                storyResults.add(new StoryDTO(story));
                addedStoryIds.add(story.getId());
            }
        }

        List<Episode> allEpisodes = episodeRepository.findAll();
        List<EpisodeDTO> episodeResults = new ArrayList<>();

        for (Episode episode : allEpisodes) {
            boolean matchesTitle =
                    episode.getTitle() != null &&
                            episode.getTitle().toLowerCase().contains(lowerQuery);

            boolean matchesContent =
                    episode.getContent() != null &&
                            episode.getContent().toLowerCase().contains(lowerQuery);

            boolean matchesComicUrl =
                    episode.getComicUrl() != null &&
                            episode.getComicUrl().toLowerCase().contains(lowerQuery);

            if (matchesTitle || matchesContent || matchesComicUrl) {
                episodeResults.add(new EpisodeDTO(episode));

                Story parent = episode.getStory();
                if (parent != null && !addedStoryIds.contains(parent.getId())) {
                    storyResults.add(new StoryDTO(parent));
                    addedStoryIds.add(parent.getId());
                }
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("users", users);
        response.put("stories", storyResults);
        response.put("episodes", episodeResults);

        return ResponseEntity.ok(response);
    }
}