package com.vonk.storyquest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vonk.storyquest.dto.CommentDTO;
import com.vonk.storyquest.model.Comment;
import com.vonk.storyquest.model.Episode;
import com.vonk.storyquest.model.User;
import com.vonk.storyquest.service.CommentService;
import com.vonk.storyquest.service.EpisodeService;
import com.vonk.storyquest.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CommentControllerTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private CommentService commentService;

    @Mock
    private EpisodeService episodeService;

    @Mock
    private UserService userService;

    @InjectMocks
    private CommentController commentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();
    }

    @Test
    void getComments() throws Exception {
        when(commentService.getCommentsByEpisode(1L)).thenReturn(List.of());

        mockMvc.perform(get("/api/episodes/1/comments"))
                .andExpect(status().isOk());
    }

    @Test
    void addComment() throws Exception {
        // Arrange
        CommentDTO request = new CommentDTO();
        request.setUserId(1L);
        request.setTextContent("Hallo");

        Comment comment = new Comment();
        comment.setTextContent("Hallo");

        when(commentService.addComment(1L, 1L, "Hallo")).thenReturn(comment);

        // Act & Assert
        mockMvc.perform(post("/api/episodes/1/comments/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteComment() throws Exception {
        // Arrange
        CommentDTO request = new CommentDTO();
        request.setUserId(1L);

        Comment comment = new Comment();
        User user = new User();
        user.setId(1L);
        user.setRole("MOD");

        comment.setUser(user);

        when(commentService.getCommentById(1L)).thenReturn(comment);
        when(userService.getUserById(1L)).thenReturn(Optional.of(user));

        // Act & Assert
        mockMvc.perform(delete("/api/episodes/comments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void readEpisode() throws Exception {
        Episode episode = new Episode();
        episode.setId(1L);

        when(episodeService.getEpisodeById(1L)).thenReturn(episode);
        when(commentService.getCommentsByEpisode(1L)).thenReturn(List.of());

        mockMvc.perform(get("/api/episodes/1/read"))
                .andExpect(status().isOk());
    }
}