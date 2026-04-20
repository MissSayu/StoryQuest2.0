package com.vonk.storyquest.dto;

import com.vonk.storyquest.model.Story;

import java.time.LocalDateTime;

public class StoryDTO {

    private Long id;
    private String title;
    private String description;
    private String type;
    private String coverImage;
    private String status;
    private LocalDateTime publishDate;
    private AuthorDTO author;

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
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getType() { return type; }
    public String getCoverImage() { return coverImage; }
    public String getStatus() { return status; }
    public LocalDateTime getPublishDate() { return publishDate; }
    public AuthorDTO getAuthor() { return author; }
}