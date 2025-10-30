package com.vonk.storyquest.repository;

import com.vonk.storyquest.model.Story;
import com.vonk.storyquest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoryRepository extends JpaRepository<Story, Long> {

    List<Story> findByUserUsername(String username);

    List<Story> findByUserId(Long userId);

    List<Story> findByTitleContainingIgnoreCase(String title);
    long countByUser(User user);
}
