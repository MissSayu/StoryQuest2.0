package com.vonk.storyquest.service;

import com.vonk.storyquest.model.Episode;
import com.vonk.storyquest.model.Story;
import com.vonk.storyquest.model.User;
import com.vonk.storyquest.repository.EpisodeRepository;
import com.vonk.storyquest.repository.StoryRepository;
import com.vonk.storyquest.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class StoryServiceTest {

    @Mock
    StoryRepository storyRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    EpisodeRepository episodeRepository;

    @InjectMocks
    StoryService storyService;

    @Captor
    ArgumentCaptor<Story> storyCaptor;

    User user;
    Story story;
    Episode episode;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);

        story = new Story();
        story.setId(1L);
        story.setTitle("Test Story");

        episode = new Episode();
        episode.setTitle("Test Story");
        episode.setEpisodeOrder(1);
    }

    @Test
    void createStory_validUser_createsStoryAndEpisode() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(storyRepository.save(any(Story.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Story result = storyService.createStory(
                "Title",
                "Description",
                "Fantasy",
                "Novel",
                1L,
                "cover.png",
                "DRAFT",
                "Episode content"
        );

        // Assert
        assertNotNull(result);
        assertEquals("Title", result.getTitle());
        assertEquals(1, result.getEpisodes().size());
        verify(episodeRepository, times(1)).save(any(Episode.class));
    }

    @Test
    void createStory_userNotFound_throwsException() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () ->
                storyService.createStory(
                        "Title",
                        "Desc",
                        "Fantasy",
                        "Novel",
                        1L,
                        null,
                        "DRAFT",
                        null
                )
        );
    }

    @Test
    void createStory_nullEpisodeContent_setsEmptyString() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(storyRepository.save(any()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Story result = storyService.createStory(
                "T",
                "D",
                "G",
                "Type",
                1L,
                null,
                "DRAFT",
                null
        );

        // Assert
        assertEquals("", result.getEpisodes().get(0).getContent());
    }

    @Test
    void getStoriesByUserIdIncludingDrafts() {
        // Arrange
        when(storyRepository.findByUserId(1L))
                .thenReturn(List.of(story));

        // Act
        List<Story> storiesFound =
                storyService.getStoriesByUserIdIncludingDrafts(1L);

        // Assert
        assertEquals(1, storiesFound.size());
        assertEquals("Test Story", storiesFound.get(0).getTitle());
    }

    @Test
    void getStoriesByUsername() {
        // Arrange
        when(storyRepository.findByUserUsername("sayu"))
                .thenReturn(List.of(story));

        // Act
        List<Story> storiesFound =
                storyService.getStoriesByUsername("sayu");

        // Assert
        assertEquals(1, storiesFound.size());
    }

    @Test
    void getStoryById_storyExists() {
        // Arrange
        when(storyRepository.findById(1L))
                .thenReturn(Optional.of(story));
        when(episodeRepository.findByStory(story))
                .thenReturn(List.of(episode));

        // Act
        Story foundStory = storyService.getStoryById(1L);

        // Assert
        assertEquals(1, foundStory.getEpisodes().size());
    }

    @Test
    void getStoryById_storyNotFound_throwsException() {
        // Arrange
        when(storyRepository.findById(1L))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class,
                () -> storyService.getStoryById(1L));
    }

    @Test
    void getAllStories() {
        // Arrange
        when(storyRepository.findAll())
                .thenReturn(List.of(story));

        // Act
        List<Story> result = storyService.getAllStories();

        // Assert
        assertEquals(1, result.size());
    }

    @Test
    void getRandomStories_limitApplied() {
        // Arrange
        when(storyRepository.findAll())
                .thenReturn(List.of(new Story(), new Story(), new Story()));

        // Act
        List<Story> result = storyService.getRandomStories(1);

        // Assert
        assertEquals(1, result.size());
    }

    @Test
    void getRandomStories_countLargerThanList_returnsAll() {
        // Arrange
        when(storyRepository.findAll())
                .thenReturn(List.of(new Story()));

        // Act
        List<Story> result = storyService.getRandomStories(5);

        // Assert
        assertEquals(1, result.size());
    }
}
