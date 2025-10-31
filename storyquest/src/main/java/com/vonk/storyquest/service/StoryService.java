package com.vonk.storyquest.service;

import com.vonk.storyquest.model.Story;
import com.vonk.storyquest.model.User;
import com.vonk.storyquest.model.Episode;
import com.vonk.storyquest.repository.StoryRepository;
import com.vonk.storyquest.repository.UserRepository;
import com.vonk.storyquest.repository.EpisodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StoryService {

    private final StoryRepository storyRepository;
    private final UserRepository userRepository;
    private final EpisodeRepository episodeRepository;

    @Autowired
    public StoryService(StoryRepository storyRepository, UserRepository userRepository,
                        EpisodeRepository episodeRepository) {
        this.storyRepository = storyRepository;
        this.userRepository = userRepository;
        this.episodeRepository = episodeRepository;
    }


    public Story createStory(String title, String description, String genre, String type,
                             Long userId, String coverImageUrl, String status, String episode1Content) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Story story = new Story();
        story.setTitle(title);
        story.setDescription(description);
        story.setGenre(genre);
        story.setType(type);
        story.setUser(user);
        story.setCoverImage(coverImageUrl);
        story.setStatus(status);

        Story savedStory = storyRepository.save(story);


        Episode firstEpisode = new Episode();
        firstEpisode.setTitle(title);
        firstEpisode.setContent(episode1Content != null ? episode1Content : "");
        firstEpisode.setEpisodeOrder(1);
        firstEpisode.setStory(savedStory);
        episodeRepository.save(firstEpisode);

        savedStory.setEpisodes(List.of(firstEpisode));
        return savedStory;
    }


    public List<Story> getStoriesByUserIdIncludingDrafts(Long userId) {
        return storyRepository.findByUserId(userId);
    }

    public List<Story> getStoriesByUsername(String username) {
        return storyRepository.findByUserUsername(username);
    }

    public Story getStoryById(Long id) {
        Story story = storyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Story not found with id: " + id));
        story.setEpisodes(episodeRepository.findByStory(story));
        return story;
    }

    public List<Story> getAllStories() {
        return storyRepository.findAll();
    }

    public List<Story> getRandomStories(int count) {
        List<Story> allStories = storyRepository.findAll();
        Collections.shuffle(allStories);
        return allStories.stream().limit(count).collect(Collectors.toList());
    }
}

