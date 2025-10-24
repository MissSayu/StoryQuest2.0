package com.vonk.storyquest.repository;

import com.vonk.storyquest.model.Story;
import com.vonk.storyquest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoryRepository extends JpaRepository<Story, Long> {

    // Get all stories for a user by username
    List<Story> findByUserUsername(String username);

    // Get all stories for a user by userId
    List<Story> findByUserId(Long userId);

    // Count all stories for a user
    long countByUser(User user);
}
