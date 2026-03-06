package com.vonk.storyquest.service;

import com.vonk.storyquest.model.FollowRelation;
import com.vonk.storyquest.model.Story;
import com.vonk.storyquest.model.User;
import com.vonk.storyquest.repository.FollowRelationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FollowRelationService {

    private final FollowRelationRepository followRepository;

    public FollowRelationService(FollowRelationRepository followRepository) {
        this.followRepository = followRepository;
    }

    // ===== USER FOLLOWS =====
    public void addFollow(User follower, User followed) {
        if (follower == null || followed == null) return;
        if (followRepository.existsByFollowerAndFollowed(follower, followed)) return;

        FollowRelation relation = new FollowRelation();
        relation.setFollower(follower);
        relation.setFollowed(followed);
        followRepository.save(relation);
    }

    public void removeFollow(User follower, User followed) {
        if (follower == null || followed == null) return;
        followRepository.findByFollowerAndFollowed(follower, followed)
                .ifPresent(followRepository::delete);
    }

    public boolean isFollowing(User follower, User followed) {
        if (follower == null || followed == null) return false;
        return followRepository.existsByFollowerAndFollowed(follower, followed);
    }

    public List<User> getFollowing(User user) {
        // Return all users that this user is following
        return followRepository.findByFollower(user).stream()
                .map(FollowRelation::getFollowed)
                .collect(Collectors.toList());
    }

    public List<User> getFollowers(User user) {
        // Return all users that are following this user
        return followRepository.findByFollowed(user).stream()
                .map(FollowRelation::getFollower)
                .collect(Collectors.toList());
    }

    // ===== STORY FOLLOWS =====
    public void addFollowStory(User follower, Story story) {
        if (follower == null || story == null) return;
        if (followRepository.existsByFollowerAndStory(follower, story)) return;

        FollowRelation relation = new FollowRelation();
        relation.setFollower(follower);
        relation.setStory(story);
        followRepository.save(relation);
    }

    public void removeFollowStory(User follower, Story story) {
        if (follower == null || story == null) return;
        followRepository.findByFollowerAndStory(follower, story)
                .ifPresent(followRepository::delete);
    }

    public boolean isFollowingStory(User follower, Story story) {
        if (follower == null || story == null) return false;
        return followRepository.existsByFollowerAndStory(follower, story);
    }

    public List<User> getFollowersStory(Story story) {
        // Return all users following this story
        return followRepository.findByStory(story).stream()
                .map(FollowRelation::getFollower)
                .collect(Collectors.toList());
    }
}
