package com.vonk.storyquest.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "episode")
public class Episode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(name = "episode_order")
    private int episodeOrder;

    private LocalDateTime publicationDate;

    @ManyToOne
    @JoinColumn(name = "story_id", nullable = false)
    private Story story;

    // ===== Constructors =====
    public Episode() {
        // Default constructor
    }

    public Episode(String title, String content, int episodeOrder) {
        this.title = title;
        this.content = content;
        this.episodeOrder = episodeOrder;
        this.publicationDate = LocalDateTime.now();
    }

    // ===== Getters and Setters =====
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getEpisodeOrder() {
        return episodeOrder;
    }

    public void setEpisodeOrder(int episodeOrder) {
        this.episodeOrder = episodeOrder;
    }

    public LocalDateTime getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDateTime publicationDate) {
        this.publicationDate = publicationDate;
    }

    public Story getStory() {
        return story;
    }

    public void setStory(Story story) {
        this.story = story;
    }
}
