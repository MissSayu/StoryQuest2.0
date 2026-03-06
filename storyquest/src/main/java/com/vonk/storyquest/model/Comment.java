package com.vonk.storyquest.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "episode_id", nullable = false)
    private Episode episode;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String textContent;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // ===== Constructors =====
    public Comment() {}

    public Comment(User user, Episode episode, String textContent) {
        this.user = user;
        this.episode = episode;
        this.textContent = textContent;
        this.createdAt = LocalDateTime.now();
    }

    // ===== Getters and Setters =====
    public Long getId() { return id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Episode getEpisode() { return episode; }
    public void setEpisode(Episode episode) { this.episode = episode; }

    public String getTextContent() { return textContent; }
    public void setTextContent(String textContent) { this.textContent = textContent; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
