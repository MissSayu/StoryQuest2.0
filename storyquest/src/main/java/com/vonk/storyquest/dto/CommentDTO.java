package com.vonk.storyquest.dto;

import com.vonk.storyquest.model.Comment;
import com.vonk.storyquest.model.User;

import java.time.LocalDateTime;

public class CommentDTO {

    private Long id;
    private Long userId;
    private String username;
    private String avatarUrl;
    private String textContent;
    private LocalDateTime createdAt;

    public CommentDTO() {
    }

    public CommentDTO(Comment comment) {
        User user = comment.getUser();

        this.id = comment.getId();
        this.userId = user != null ? user.getId() : null;
        this.username = user != null ? user.getUsername() : "Onbekend";

        if (user != null && user.getProfile() != null && user.getProfile().getAvatarUrl() != null) {
            this.avatarUrl = user.getProfile().getAvatarUrl();
        } else {
            this.avatarUrl = "/placeholders/avatar-placeholder.png";
        }

        this.textContent = comment.getTextContent();
        this.createdAt = comment.getCreatedAt();
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getTextContent() {
        return textContent;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
