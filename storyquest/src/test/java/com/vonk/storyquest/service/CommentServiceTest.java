package com.vonk.storyquest.service;

import com.vonk.storyquest.model.Comment;
import com.vonk.storyquest.model.Episode;
import com.vonk.storyquest.model.User;
import com.vonk.storyquest.repository.CommentRepository;
import com.vonk.storyquest.repository.EpisodeRepository;
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
class CommentServiceTest {

    @Mock
    CommentRepository commentRepository;

    @Mock
    EpisodeRepository episodeRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    CommentService commentService;

    @Captor
    ArgumentCaptor<Comment> commentCaptor;

    Episode episode;
    User user;
    Comment comment;

    @BeforeEach
    void setUp() {
        episode = new Episode();
        episode.setId(1L);

        user = new User();
        user.setId(1L);

        comment = new Comment(user, episode, "Test comment");
    }

    @Test
    void getCommentsByEpisode_episodeExists_returnsComments() {
        // Arrange
        when(episodeRepository.findById(1L)).thenReturn(Optional.of(episode));
        when(commentRepository.findByEpisodeOrderByCreatedAtAsc(episode))
                .thenReturn(List.of(comment));

        // Act
        List<Comment> result = commentService.getCommentsByEpisode(1L);

        // Assert
        assertEquals(1, result.size());
        assertEquals("Test comment", result.get(0).getTextContent());
    }

    @Test
    void getCommentsByEpisode_episodeNotFound_throwsException() {
        // Arrange
        when(episodeRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class,
                () -> commentService.getCommentsByEpisode(1L));
    }

    @Test
    void addComment_validData_commentSaved() {
        // Arrange
        when(episodeRepository.findById(1L)).thenReturn(Optional.of(episode));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(commentRepository.save(any(Comment.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Comment result = commentService.addComment(1L, 1L, " Hello world ");

        // Assert
        assertEquals("Hello world", result.getTextContent());
        verify(commentRepository).save(commentCaptor.capture());
    }

    @Test
    void addComment_episodeNotFound_throwsException() {
        // Arrange
        when(episodeRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class,
                () -> commentService.addComment(1L, 1L, "Test"));
    }

    @Test
    void addComment_userNotFound_throwsException() {
        // Arrange
        when(episodeRepository.findById(1L)).thenReturn(Optional.of(episode));
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class,
                () -> commentService.addComment(1L, 1L, "Test"));
    }

    @Test
    void addComment_nullText_throwsException() {
        // Arrange
        when(episodeRepository.findById(1L)).thenReturn(Optional.of(episode));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> commentService.addComment(1L, 1L, null));
    }

    @Test
    void addComment_emptyText_throwsException() {
        // Arrange
        when(episodeRepository.findById(1L)).thenReturn(Optional.of(episode));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> commentService.addComment(1L, 1L, "   "));
    }

    @Test
    void addComment_textWithQuotes_quotesAreRemoved() {
        // Arrange
        when(episodeRepository.findById(1L)).thenReturn(Optional.of(episode));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(commentRepository.save(any(Comment.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Comment result = commentService.addComment(1L, 1L, "\"Quoted text\"");

        // Assert
        assertEquals("Quoted text", result.getTextContent());
    }

    @Test
    void getCommentById_commentExists_returnsComment() {
        // Arrange
        when(commentRepository.findById(1L))
                .thenReturn(Optional.of(comment));

        // Act
        Comment result = commentService.getCommentById(1L);

        // Assert
        assertEquals("Test comment", result.getTextContent());
    }

    @Test
    void getCommentById_commentNotFound_throwsException() {
        // Arrange
        when(commentRepository.findById(1L))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class,
                () -> commentService.getCommentById(1L));
    }

    @Test
    void deleteComment_deletesById() {
        // Act
        commentService.deleteComment(1L);

        // Assert
        verify(commentRepository).deleteById(1L);
    }
}
