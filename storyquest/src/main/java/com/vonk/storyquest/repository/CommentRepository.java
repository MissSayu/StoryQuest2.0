package com.vonk.storyquest.repository;

import com.vonk.storyquest.model.Comment;
import com.vonk.storyquest.model.Episode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByEpisodeOrderByCreatedAtAsc(Episode episode);
}
