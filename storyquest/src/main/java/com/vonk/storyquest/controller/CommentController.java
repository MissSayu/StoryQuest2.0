package com.vonk.storyquest.controller;

import com.vonk.storyquest.dto.CommentDTO;
import com.vonk.storyquest.model.Comment;
import com.vonk.storyquest.model.Episode;
import com.vonk.storyquest.model.User;
import com.vonk.storyquest.service.CommentService;
import com.vonk.storyquest.service.EpisodeService;
import com.vonk.storyquest.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/episodes")
@CrossOrigin(origins = "http://localhost:5173")
public class CommentController {

    private final CommentService commentService;
    private final EpisodeService episodeService;
    private final UserService userService;

    public CommentController(CommentService commentService,
                             EpisodeService episodeService,
                             UserService userService) {
        this.commentService = commentService;
        this.episodeService = episodeService;
        this.userService = userService;
    }


    @GetMapping("/{episodeId}/comments")
    public ResponseEntity<List<CommentDTO>> getComments(@PathVariable Long episodeId) {
        List<CommentDTO> comments = commentService.getCommentsByEpisode(episodeId)
                .stream()
                .map(CommentDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(comments);
    }


    @PostMapping("/{episodeId}/comments/add")
    public ResponseEntity<CommentDTO> addComment(
            @PathVariable Long episodeId,
            @RequestParam Long userId,
            @RequestParam String textContent
    ) {
        Comment comment = commentService.addComment(episodeId, userId, textContent);
        return ResponseEntity.ok(new CommentDTO(comment));
    }


    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId, @RequestParam Long userId) {
        Comment comment = commentService.getCommentById(commentId);
        User user = userService.getUserById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));


        if (!comment.getUser().getId().equals(userId) && !"MOD".equalsIgnoreCase(user.getRole())) {
            return ResponseEntity.status(403).body("Je hebt geen toestemming om deze reactie te verwijderen.");
        }

        commentService.deleteComment(commentId);
        return ResponseEntity.ok("Comment deleted successfully");
    }


    @GetMapping("/{episodeId}/read")
    public ResponseEntity<?> readEpisode(@PathVariable Long episodeId) {
        Episode episode = episodeService.getEpisodeById(episodeId);
        List<CommentDTO> comments = commentService.getCommentsByEpisode(episodeId)
                .stream()
                .map(CommentDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(Map.of(
                "episode", episode,
                "comments", comments
        ));
    }
}
