package com.vonk.storyquest.controller;

import com.vonk.storyquest.model.Story;
import com.vonk.storyquest.model.User;
import com.vonk.storyquest.repository.StoryRepository;
import com.vonk.storyquest.repository.UserRepository;
import com.vonk.storyquest.service.FollowRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/follow")
@CrossOrigin(origins = "http://localhost:5173")
public class FollowRelationController {

    @Autowired
    private FollowRelationService followService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StoryRepository storyRepository;


    @GetMapping("/check")
    public ResponseEntity<Boolean> checkFollowStory(
            @RequestParam Long followerId,
            @RequestParam Long followedStoryId) {
        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Story story = storyRepository.findById(followedStoryId)
                .orElseThrow(() -> new RuntimeException("Story not found"));
        boolean isFollowing = followService.isFollowingStory(follower, story);
        return ResponseEntity.ok(isFollowing);
    }

    @PostMapping("/{followerId}/follow/{storyId}")
    public ResponseEntity<Void> followStory(
            @PathVariable Long followerId,
            @PathVariable Long storyId) {
        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new RuntimeException("Story not found"));
        followService.addFollowStory(follower, story);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{followerId}/unfollow/{storyId}")
    public ResponseEntity<Void> unfollowStory(
            @PathVariable Long followerId,
            @PathVariable Long storyId) {
        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Story story = storyRepository.findById(storyId)
                .orElseThrow(() -> new RuntimeException("Story not found"));
        followService.removeFollowStory(follower, story);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{followerId}/isFollowing/{authorId}")
    public ResponseEntity<Boolean> isFollowingAuthor(
            @PathVariable Long followerId,
            @PathVariable Long authorId) {
        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        User author = userRepository.findById(authorId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        boolean isFollowing = followService.isFollowing(follower, author);
        return ResponseEntity.ok(isFollowing);
    }

    @PostMapping("/{followerId}/followAuthor/{authorId}")
    public ResponseEntity<Void> followAuthor(
            @PathVariable Long followerId,
            @PathVariable Long authorId) {
        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        User author = userRepository.findById(authorId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        followService.addFollow(follower, author);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{followerId}/unfollowAuthor/{authorId}")
    public ResponseEntity<Void> unfollowAuthor(
            @PathVariable Long followerId,
            @PathVariable Long authorId) {
        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        User author = userRepository.findById(authorId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        followService.removeFollow(follower, author);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/followers/count/{userId}")
    public ResponseEntity<Long> countFollowers(@PathVariable Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        long count = followService.getFollowers(user).size();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/following/count/{userId}")
    public ResponseEntity<Long> countFollowing(@PathVariable Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        long count = followService.getFollowing(user).size();
        return ResponseEntity.ok(count);
    }
}
