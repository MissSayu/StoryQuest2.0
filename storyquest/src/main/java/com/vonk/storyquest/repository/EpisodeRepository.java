package com.vonk.storyquest.repository;

import com.vonk.storyquest.model.Episode;
import com.vonk.storyquest.model.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EpisodeRepository extends JpaRepository<Episode, Long> {
    List<Episode> findByStoryIdOrderByEpisodeOrderAsc(Long storyId);

    List<Episode> findByStory(Story story);
}
