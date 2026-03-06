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
        this.bio = user.getBio();
        this.avatarUrl = user.getAvatarUrl();
    }


    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getBio() { return bio; }
    public String getAvatarUrl() { return avatarUrl; }
}
