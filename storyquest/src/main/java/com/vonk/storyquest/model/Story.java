package com.vonk.storyquest.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "story")
public class Story {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String type;

    private String status;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // ensures a story must have a user
    private User user;

    @OneToMany(mappedBy = "story", cascade = CascadeType.ALL)
    private List<Episode> episodes;

    // Correct setStatus method
    public void setStatus(String status) {
        this.status = status;
    }

    // Setter for user
    public void setUser(User user) {
        this.user = user;
    }

    // Getters
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getType() { return type; }
    public String getStatus() { return status; }
    public User getUser() { return user; }
    public List<Episode> getEpisodes() { return episodes; }

    // Setters for other fields
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setType(String type) { this.type = type; }
    public void setEpisodes(List<Episode> episodes) { this.episodes = episodes; }
}
