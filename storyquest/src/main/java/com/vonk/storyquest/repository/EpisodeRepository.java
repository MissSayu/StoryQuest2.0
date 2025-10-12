package com.vonk.storyquest.repository;

import com.vonk.storyquest.model.Episode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EpisodeRepository extends JpaRepository<Episode, Long> {
    List<Episode> findByStoryIdOrderByEpisodeOrder(Long storyId);
}