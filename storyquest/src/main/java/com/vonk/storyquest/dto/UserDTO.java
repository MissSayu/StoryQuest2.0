package com.vonk.storyquest.dto;

import com.vonk.storyquest.model.User;

public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String role;

    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.role = user.getRole();
    }


    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
}
