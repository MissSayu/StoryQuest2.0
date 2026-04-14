package com.vonk.storyquest.dto;

import com.vonk.storyquest.model.User;

public class UserDTO {

    private Long id;
    private String username;
    private String email;
    private String role;
    private String bio;
    private String avatarUrl;

    public UserDTO() {}

    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.role = user.getRole();

        if (user.getProfile() != null) {
            this.bio = user.getProfile().getBio();
            this.avatarUrl = user.getProfile().getAvatarUrl();
        } else {
            this.bio = "This user hasn’t written a bio yet.";
            this.avatarUrl = "http://localhost:8081/avatars/default.jpg";
        }
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public String getBio() {
        return bio;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
