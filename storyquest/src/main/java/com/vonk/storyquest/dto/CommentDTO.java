package com.vonk.storyquest.dto;

import com.vonk.storyquest.model.Comment;

import java.time.LocalDateTime;

public class CommentDTO {

    private Long id;
    private Long userId;
    private String username;
    private String avatarUrl;
    private String textContent;
    private LocalDateTime createdAt;

    public CommentDTO(Comment comment) {
        this.id = comment.getId();
        this.userId = comment.getUser() != null ? comment.getUser().getId() : null;
        this.username = comment.getUser() != null ? comment.getUser().getUsername() : "Onbekend";
        this.avatarUrl = comment.getUser() != null ? comment.getUser().getAvatarUrl() : "/placeholders/avatar-placeholder.png";
        this.textContent = comment.getTextContent();
        this.createdAt = comment.getCreatedAt();
    }

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getAvatarUrl() { return avatarUrl; }
    public String getTextContent() { return textContent; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
