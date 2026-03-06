package com.vonk.storyquest.controller;

import com.vonk.storyquest.dto.EpisodeDTO;
import com.vonk.storyquest.dto.StoryDTO;
import com.vonk.storyquest.model.Episode;
import com.vonk.storyquest.model.Story;
import com.vonk.storyquest.model.User;
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
    public ResponseEntity<Map<String, Object>> search(@RequestParam String q) {
        String query = q.trim().toLowerCase();


        List<User> users = userRepository.findByUsernameContainingIgnoreCase(query);


        List<Episode> matchingEpisodes = episodeRepository.findByTitleContainingIgnoreCase(query);


        Set<Long> addedStoryIds = new HashSet<>();
        List<StoryDTO> storyResults = new ArrayList<>();

        List<Story> storiesByTitle = storyRepository.findByTitleContainingIgnoreCase(query);
        for (Story s : storiesByTitle) {
            storyResults.add(new StoryDTO(s));
            addedStoryIds.add(s.getId());
        }

        List<Story> allStories = storyRepository.findAll();
        for (Story s : allStories) {
            if (!addedStoryIds.contains(s.getId())) {
                if (s.getDescription() != null && s.getDescription().toLowerCase().contains(query)) {
                    storyResults.add(new StoryDTO(s));
                    addedStoryIds.add(s.getId());
                }
            }
        }


        for (Episode ep : matchingEpisodes) {
            Story parent = ep.getStory();
            if (parent != null && !addedStoryIds.contains(parent.getId())) {
                storyResults.add(new StoryDTO(parent));
                addedStoryIds.add(parent.getId());
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("users", users);
        response.put("stories", storyResults);
        response.put("episodes", matchingEpisodes.stream().map(EpisodeDTO::new).collect(Collectors.toList()));

        return ResponseEntity.ok(response);
    }
}
