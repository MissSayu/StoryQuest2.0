package com.vonk.storyquest.service;

import com.vonk.storyquest.model.Episode;
import com.vonk.storyquest.model.Story;
import com.vonk.storyquest.repository.EpisodeRepository;
import com.vonk.storyquest.repository.StoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EpisodeService {

    private final EpisodeRepository episodeRepository;
    private final StoryRepository storyRepository;

    public EpisodeService(EpisodeRepository episodeRepository, StoryRepository storyRepository) {
        this.episodeRepository = episodeRepository;
        this.storyRepository = storyRepository;
    }

    // Get all episodes for a story
    public List<Episode> getEpisodesByStory(Long storyId) {
        return episodeRepository.findByStoryIdOrderByEpisodeOrder(storyId);
    }

    // Create a new episode for a story
    public Episode addEpisode(Long storyId, Episode episode) {
        System.out.println(">>> Trying to add episode to storyId: " + storyId);
        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new RuntimeException("Story not found"));
        episode.setStory(story);
        episode.setPublicationDate(LocalDateTime.now());
        return episodeRepository.save(episode);
    }
}

