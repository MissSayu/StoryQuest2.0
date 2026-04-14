package com.vonk.storyquest.dto;

import com.vonk.storyquest.model.User;

public class AuthorDTO {
    private Long id;
    private String username;
    private String bio;
    private String avatarUrl;

    public AuthorDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();

        if (user.getProfile() != null) {
            this.bio = user.getProfile().getBio();
            this.avatarUrl = user.getProfile().getAvatarUrl();
        } else {
            this.bio = "This user hasn’t written a bio yet.";
            this.avatarUrl = "http://localhost:8081/avatars/default.jpg";
        }
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getBio() { return bio; }
    public String getAvatarUrl() { return avatarUrl; }
}
