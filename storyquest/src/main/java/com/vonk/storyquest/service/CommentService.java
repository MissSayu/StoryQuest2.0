package com.vonk.storyquest.service;

import com.vonk.storyquest.model.Comment;
import com.vonk.storyquest.model.Episode;
import com.vonk.storyquest.model.User;
import com.vonk.storyquest.repository.CommentRepository;
import com.vonk.storyquest.repository.EpisodeRepository;
import com.vonk.storyquest.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final EpisodeRepository episodeRepository;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository, EpisodeRepository episodeRepository,
                          UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.episodeRepository = episodeRepository;
        this.userRepository = userRepository;
    }

    public List<Comment> getCommentsByEpisode(Long episodeId) {
        Episode episode = episodeRepository.findById(episodeId)
                .orElseThrow(() -> new RuntimeException("Episode not found"));
        return commentRepository.findByEpisodeOrderByCreatedAtAsc(episode);
    }

    public Comment addComment(Long episodeId, Long userId, String textContent) {
        Episode episode = episodeRepository.findById(episodeId)
                .orElseThrow(() -> new RuntimeException("Episode not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (textContent == null || textContent.trim().isEmpty()) {
            throw new IllegalArgumentException("Comment cannot be empty");
        }

        String sanitizedText = textContent.trim().replaceAll("^\"|\"$", "");
        Comment comment = new Comment(user, episode, sanitizedText);
        return commentRepository.save(comment);
    }

    public Comment getCommentById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
