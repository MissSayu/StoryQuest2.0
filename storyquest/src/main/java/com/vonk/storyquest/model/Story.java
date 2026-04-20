package com.vonk.storyquest.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.List;

@Entity
@Table(name = "stories")
public class Story {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String tags;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String type;
    private String genre;

    @Column(name = "cover_image")
    private String coverImage;

    private String status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "publish_date")
    private LocalDateTime publishDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "story", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Episode> episodes;

    public Story() {}

    @PrePersist
    public void prePersist() {
        if ("published".equalsIgnoreCase(this.status) && this.publishDate == null) {
            this.publishDate = LocalDateTime.now();
        }
    }

    @PreUpdate
    public void preUpdate() {
        if ("published".equalsIgnoreCase(this.status) && this.publishDate == null) {
            this.publishDate = LocalDateTime.now();
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTags() { return tags; }
    public void setTags(String tags) { this.tags = tags; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    public String getCoverImage() { return coverImage; }
    public void setCoverImage(String coverImage) { this.coverImage = coverImage; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getPublishDate() { return publishDate; }
    public void setPublishDate(LocalDateTime publishDate) { this.publishDate = publishDate; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public List<Episode> getEpisodes() { return episodes; }
    public void setEpisodes(List<Episode> episodes) { this.episodes = episodes; }
}