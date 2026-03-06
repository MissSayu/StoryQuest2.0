package com.vonk.storyquest.controller;

import com.vonk.storyquest.model.Story;
import com.vonk.storyquest.service.EpisodeService;
import com.vonk.storyquest.service.StoryService;
import com.vonk.storyquest.repository.StoryRepository;
import com.vonk.storyquest.repository.UserRepository;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StoryControllerTest {

    @InjectMocks
    private StoryController controller;

    @Mock
    private StoryService storyService;

    @Mock
    private EpisodeService episodeService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private StoryRepository storyRepository;


    @Test
    void addResourceHandlers() {
        ResourceHandlerRegistry registry = mock(ResourceHandlerRegistry.class);
        ResourceHandlerRegistration registration = mock(ResourceHandlerRegistration.class);

        when(registry.addResourceHandler(anyString())).thenReturn(registration);
        when(registration.addResourceLocations(anyString())).thenReturn(registration);

        assertDoesNotThrow(() -> controller.addResourceHandlers(registry));
    }





    @Test
    void createStory() {

        // Arrange
        Story story = new Story();

        when(storyService.createStory(
                anyString(),
                anyString(),
                anyString(),
                anyString(),
                anyLong(),
                any(),
                any(),
                any()
        )).thenReturn(story);

        // Act
        ResponseEntity<?> response = controller.createStory(
                null,
                "Test",
                "Description",
                "Fantasy",
                "story",
                1L,
                "draft",
                "content",
                null
        );

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getStoriesByUserId() {

        // Arrange
        List<Story> stories = new ArrayList<>();
        stories.add(new Story());

        when(storyService.getStoriesByUserIdIncludingDrafts(1L))
                .thenReturn(stories);

        // Act
        ResponseEntity<?> response = controller.getStoriesByUserId(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getStoryById() {

        // Arrange
        Story story = new Story();

        when(storyService.getStoryById(1L))
                .thenReturn(story);

        // Act
        ResponseEntity<?> response = controller.getStoryById(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getStoriesByUsername() {

        // Arrange
        List<Story> stories = new ArrayList<>();
        stories.add(new Story());

        when(storyService.getStoriesByUsername("user"))
                .thenReturn(stories);

        // Act
        ResponseEntity<?> response = controller.getStoriesByUsername("user");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getAllStories() {

        // Arrange
        List<Story> stories = new ArrayList<>();
        stories.add(new Story());

        when(storyService.getAllStories()).thenReturn(stories);

        // Act
        ResponseEntity<?> response = controller.getAllStories();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getRandomStories() {

        // Arrange
        List<Story> stories = new ArrayList<>();
        stories.add(new Story());

        when(storyService.getRandomStories(3)).thenReturn(stories);

        // Act
        ResponseEntity<?> response = controller.getRandomStories(3);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
