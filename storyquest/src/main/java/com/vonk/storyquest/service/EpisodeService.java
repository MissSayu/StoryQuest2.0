package com.vonk.storyquest.service;

import com.vonk.storyquest.model.Episode;
import com.vonk.storyquest.repository.EpisodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EpisodeService {

    @Autowired
    private EpisodeRepository episodeRepository;

    // Save or update an episode
    public Episode save(Episode episode) {
        return episodeRepository.save(episode);
    }

    // Get episodes by story
    public List<Episode> getEpisodesByStoryId(Long storyId) {
        return episodeRepository.findByStoryIdOrderByEpisodeOrderAsc(storyId);
    }

    public Episode getEpisodeById(Long episodeId) {
        return episodeRepository.findById(episodeId)
                .orElse(null); // or throw an exception if you prefer
    }

}
