package com.vonk.storyquest.model;

import jakarta.persistence.*;

@Entity
@Table(name = "follow_relations")
public class FollowRelation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "follower_id")
    private User follower;

    @ManyToOne
    @JoinColumn(name = "followed_id")
    private User followed;

    @ManyToOne
    @JoinColumn(name = "story_id")
    private Story story; // optional: for story follows

    public FollowRelation() {}

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getFollower() { return follower; }
    public void setFollower(User follower) { this.follower = follower; }
    public User getFollowed() { return followed; }
    public void setFollowed(User followed) { this.followed = followed; }
    public Story getStory() { return story; }
    public void setStory(Story story) { this.story = story; }
}
