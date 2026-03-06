package com.vonk.storyquest.dto;

import com.vonk.storyquest.model.Story;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class StoryDTO {

    private Long id;
    private String title;
    private String description;
    private String type;
    private String coverImage;
    private String status;
    private LocalDateTime publishDate;

    private AuthorDTO author;
    private List<EpisodeDTO> episodes;

    public StoryDTO(Story story) {
        this.id = story.getId();
        this.title = story.getTitle();
        this.description = story.getDescription();
        this.type = story.getType();
        this.coverImage = story.getCoverImage() != null
                ? story.getCoverImage()
                : "/uploads/covers/book-cover-placeholder.png";

        this.status = story.getStatus();
        this.publishDate = story.getPublishDate();

        if (story.getUser() != null) {
            this.author = new AuthorDTO(story.getUser());
        }

        if (story.getEpisodes() != null) {
            this.episodes = story.getEpisodes().stream()
                    .map(EpisodeDTO::new)
                    .collect(Collectors.toList());
        }
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getType() { return type; }
    public String getCoverImage() { return coverImage; }
    public String getStatus() { return status; }
    public LocalDateTime getPublishDate() { return publishDate; }
    public AuthorDTO getAuthor() { return author; }
    public List<EpisodeDTO> getEpisodes() { return episodes; }
}
