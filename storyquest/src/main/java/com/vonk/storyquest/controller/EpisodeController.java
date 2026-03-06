package com.vonk.storyquest.controller;

import com.vonk.storyquest.dto.EpisodeDTO;
import com.vonk.storyquest.model.Episode;
import com.vonk.storyquest.service.EpisodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/episodes")
@CrossOrigin(origins = "http://localhost:5173")
public class EpisodeController {

    @Autowired
    private EpisodeService episodeService;

    @GetMapping("/story/{storyId}")
    public ResponseEntity<List<EpisodeDTO>> getEpisodesByStory(@PathVariable Long storyId) {
        List<Episode> episodes = episodeService.getEpisodesByStoryId(storyId);
        List<EpisodeDTO> dtos = episodes.stream().map(EpisodeDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
}
