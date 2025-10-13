package com.vonk.storyquest.service;

import com.vonk.storyquest.model.Episode;
import com.vonk.storyquest.model.Story;
import com.vonk.storyquest.repository.EpisodeRepository;
import com.vonk.storyquest.repository.StoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StoryService {

    private final StoryRepository storyRepository;
    private final EpisodeRepository episodeRepository;

    public StoryService(StoryRepository storyRepository, EpisodeRepository episodeRepository) {
        this.storyRepository = storyRepository;
        this.episodeRepository = episodeRepository;
    }

    // ===== Get all stories =====
    public List<Story> getAllStories() {
        return storyRepository.findAll();
    }

    // ===== Get story by ID =====
    public Optional<Story> getStoryById(Long id) {
        return storyRepository.findById(id);
    }

    // ===== Create new story =====
    public Story createStory(Story story) {
        story.setStatus("draft"); // default status
        return storyRepository.save(story);
    }

    // ===== Update existing story =====
    public Story updateStory(Story story) {
        return storyRepository.save(story);
    }

    // ===== Add episode to story =====
    public Episode addEpisode(Long storyId, Episode episode) {
        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new RuntimeException("Story not found"));
        episode.setStory(story);
        return episodeRepository.save(episode);
    }

    // ===== Get all episodes of a story =====
    public List<Episode> getEpisodes(Long storyId) {
        return episodeRepository.findByStoryIdOrderByEpisodeOrder(storyId);
    }

    // ===== Get stories by user =====
    public List<Story> getStoriesByUser(Long userId) {
        return storyRepository.findByUserId(userId);
    }
}
