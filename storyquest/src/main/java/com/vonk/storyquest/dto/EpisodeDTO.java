package com.vonk.storyquest.dto;

import com.vonk.storyquest.model.Episode;

import java.time.LocalDateTime;

public class EpisodeDTO {

    private Long id;
    private String title;
    private String content;
    private Long storyId;
    private int episodeOrder;
    private LocalDateTime publicationDate;
    private String coverUrl;
    private String storyCoverImage;
    private int likesCount = 0;
    private int commentsCount = 0;

    public EpisodeDTO(Episode episode) {
        this.id = episode.getId();
        this.title = episode.getTitle();
        this.content = episode.getContent();
        this.storyId = episode.getStory() != null ? episode.getStory().getId() : null;
        this.episodeOrder = episode.getEpisodeOrder();
        this.publicationDate = episode.getPublicationDate();
        this.coverUrl = episode.getCoverUrl();
        this.storyCoverImage = episode.getStory() != null ? episode.getStory().getCoverImage() : null;
    }


    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public Long getStoryId() { return storyId; }
    public int getEpisodeOrder() { return episodeOrder; }
    public LocalDateTime getPublicationDate() { return publicationDate; }
    public String getCoverUrl() { return coverUrl; }
    public int getLikesCount() { return likesCount; }
    public int getCommentsCount() { return commentsCount; }
    public String getStoryCoverImage() { return storyCoverImage; }
}
